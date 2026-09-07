package com.back.p67260811.global.rq;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.domain.member.service.MemberService;
import com.back.p67260811.global.exception.ServiceException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {

    private final MemberService memberService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public Member getActor() {
        String authorization = this.getHeader("Authorization", "");
        String accessToken;
        String refreshToken;

        if (!authorization.isBlank()) {

            if (!authorization.startsWith("Bearer ")) {
                throw new ServiceException("401-2",
                        "헤더의 인증 정보 형식이 올바르지 않습니다.");
            }
            String[] parts = authorization.split(" ", 3);
            refreshToken = parts[1];
            accessToken = parts.length == 3 ? parts[2] : "";

        } else {
            refreshToken = getCookieValue("refreshToken", "");
            accessToken = getCookieValue("accessToken", "");
        }

        if (refreshToken.isBlank())
            throw new ServiceException("401-1", "로그인 후 이용해주세요.");
        Member member = null;

        if (!accessToken.isBlank()) {
            Map<String, Object> payload = memberService.payloadOrNull(accessToken);

            if (payload != null) {
                int id = (int) payload.get("id");
                String username = (String) payload.get("username");
                member = new Member(id, username);
            }
        }

        if (member == null) {
            member = memberService
                    .findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new ServiceException(
                            "401-3", "Refresh Token이 유효하지 않습니다."));
        }

        return member;
    }

    public void setCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        if (value.isBlank()) {
            cookie.setMaxAge(0);
        }

        response.addCookie(cookie);
    }

    public void deleteCookie(String name) {
        setCookie(name, null);
    }

    private String getCookieValue(String name, String defaultValue) {
        return Optional
                .ofNullable(request.getCookies())
                .flatMap(
                        cookies ->
                                Arrays.stream(cookies)
                                        .filter(cookie -> cookie.getName().equals(name))
                                        .map(Cookie::getValue)
                                        .filter(value -> !value.isBlank())
                                        .findFirst()
                )
                .orElse(defaultValue);
    }

    private String getHeader(String name, String defaultValue) {
        return Optional
                .ofNullable(request.getHeader(name))
                .filter(headerValue -> !headerValue.isBlank())
                .orElse(defaultValue);
    }
}
