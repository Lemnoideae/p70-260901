package com.back.p67260811.domain.post.post.dto;

import com.back.p67260811.domain.post.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String title,
        String content,
        String nickname,
        String username
) {
    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createDate(post.getCreateDate())
                .modifyDate(post.getModifyDate())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getAuthor().getNickname())
                .username(post.getAuthor().getUsername())
                .build();
    }
}
