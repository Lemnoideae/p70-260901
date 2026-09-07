package com.back.p67260811.domain.member.dto.login;

import com.back.p67260811.domain.member.dto.MemberDto;

public record LoginResBody(
        MemberDto memberDto,
        String accessToken,
        String refreshToken
) {
}
