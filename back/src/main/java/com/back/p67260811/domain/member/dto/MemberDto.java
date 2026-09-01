package com.back.p67260811.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberDto(
        String nickname,
        String username
) {
    public static MemberDto from(String nickname, String username) {
        return MemberDto.builder()
                .nickname(nickname)
                .username(username)
                .build();
    }
}
