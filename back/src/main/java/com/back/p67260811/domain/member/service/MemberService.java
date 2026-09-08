package com.back.p67260811.domain.member.service;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.domain.member.repository.MemberRepository;
import com.back.p67260811.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthTokenService authTokenService;
    private final PasswordEncoder passwordEncoder;

    public long count() {
        return memberRepository.count();
    }

    public Member join(String username, String rawPassword, String nickname) {
        findByUsername(username).ifPresent(_ -> {
            throw new ServiceException("409-1", "이미 사용 중인 아이디입니다.");});
        return memberRepository.save(
                new Member(username, passwordEncoder.encode(rawPassword), nickname));
    }

    public Member join(String username, String rawPassword, String nickname, String refreshToken) {
        findByUsername(username).ifPresent(_ -> {
            throw new ServiceException("409-1", "이미 사용 중인 아이디입니다.");});
        return memberRepository.save(
                new Member(username, passwordEncoder.encode(rawPassword),
                        nickname, refreshToken));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findByUsername(String user) {
        return memberRepository.findByUsername(user);
    }

    public Optional<Member> findByRefreshToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken);
    }

    public String genAccessToken(Member member) {
        return authTokenService.genAccessToken(member);
    }

    public Map<String, Object> payloadOrNull(String accessToken) {
        return authTokenService.payloadOrNull(accessToken);
    }

    public Optional<Member> findById(int id) {
        return memberRepository.findById(id);
    }

    public void checkPassword(String rawPassword, String encodedPassword) {
        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ServiceException("401-2", "비밀번호가 일치하지 않습니다.");
        }
    }
}