package com.back.p67260811.domain.member.controller;

import com.back.p67260811.domain.member.dto.MemberDto;
import com.back.p67260811.domain.member.dto.join.JoinReqBody;
import com.back.p67260811.domain.member.dto.join.JoinResBody;
import com.back.p67260811.domain.member.dto.login.LoginReqBody;
import com.back.p67260811.domain.member.dto.login.LoginResBody;
import com.back.p67260811.domain.member.dto.me.MeResBody;
import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.domain.member.service.MemberService;
import com.back.p67260811.global.dto.RsData;
import com.back.p67260811.global.exception.ServiceException;
import com.back.p67260811.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @PostMapping("/join")
    public RsData<MemberDto> join(@RequestBody @Valid JoinReqBody reqBody) {
        Member member = memberService.join(
                reqBody.username(),
                reqBody.password(),
                reqBody.nickname());

        return new RsData(
                "201-1",
                "회원가입이 완료되었습니다. %s님 환영합니다."
                        .formatted(reqBody.nickname()),
                new JoinResBody(MemberDto.from(member))
        );
    }

    @PostMapping("/login")
    public RsData<MemberDto> login(
            @RequestBody @Valid LoginReqBody reqBody
    ) {

        Member actor = memberService.findByUsername(
                reqBody.username()).orElseThrow(() ->
                new ServiceException("401-1", "존재하지 않는 아이디입니다."));

        memberService.checkPassword(reqBody.password(), actor.getPassword());
        String accessToken = memberService.genAccessToken(actor);
        rq.addCookie("refreshToken", actor.getRefreshToken());
        rq.addCookie("accessToken", accessToken);

        return new RsData("200-1",
                "%s님 환영합니다.".formatted(actor.getNickname()),
                new LoginResBody(MemberDto.from(actor), accessToken, actor.getRefreshToken()));
    }

    @GetMapping("/me")
    public RsData<MemberDto> me() {
        Member actor = rq.getActor();
        Member realActor = memberService.findById(actor.getId()).orElseThrow();
        return new RsData("200-1", "OK",
                new MeResBody(MemberDto.from(realActor)));
    }

    @DeleteMapping("/logout")
    public RsData<Void> logout() {
        rq.deleteCookie("refreshToken");
        rq.deleteCookie("accessToken");
        return new RsData<>("200-1", "로그아웃 되었습니다.");
    }
}