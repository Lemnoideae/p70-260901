package com.back.p67260811.domain.member.dto;

import com.back.p67260811.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String name
) {
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .createDate(member.getCreateDate())
                .modifyDate(member.getModifyDate())
                .name(member.getUsername())
                .build();
    }
}
