package com.back.p67260811.domain.post.post.dto.modify;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostModifyReqBody(
        @Size(min = 2, max = 10, message = "제목은 2글자 이상 10글자 이하로 작성해주세요.")
        @NotBlank(message = "제목을 입력해주세요.")
        String title,

        @Size(min = 2, max = 10, message = "내용은 2글자 이상 10글자 이하로 작성해주세요.")
        @NotBlank(message = "내용을 입력해주세요.")
        String content
) {
}
