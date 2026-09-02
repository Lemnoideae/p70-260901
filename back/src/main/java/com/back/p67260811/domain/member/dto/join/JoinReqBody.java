package com.back.p67260811.domain.member.dto.join;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JoinReqBody(
        @NotBlank
        @Size(min = 2, max = 30)
        String username,

        @NotBlank
        @Size(min = 2, max = 30)
        String password,

        @NotBlank
        @Size(min = 2, max = 30)
        String nickname
) {

}
