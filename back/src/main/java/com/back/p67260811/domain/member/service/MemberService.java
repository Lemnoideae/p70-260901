package com.back.p67260811.domain.member.service;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.domain.member.repository.MemberRepository;
import com.back.p67260811.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthTokenService authTokenService;

    public long count() {
        return memberRepository.count();
    }

    public Member join(String username, String password, String nickname) {
        findByUsername(username).ifPresent(_ -> {
            throw new ServiceException("409-1", "이미 사용 중인 아이디입니다.");});
        return memberRepository.save(new Member(username, password, nickname));
    }

    public Member join(String username, String password, String nickname, String apiKey) {
        findByUsername(username).ifPresent(_ -> {
            throw new ServiceException("409-1", "이미 사용 중인 아이디입니다.");});
        return memberRepository.save(new Member(username, password, nickname, apiKey));
    }

    public Optional<Member> findByUsername(String user) {
        return memberRepository.findByUsername(user);
    }

    public Optional<Member> findByApiKey(String apiKey) {
        return memberRepository.findByApiKey(apiKey);
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
}