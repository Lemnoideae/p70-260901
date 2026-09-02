package com.back.p67260811.domain.member.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginReqBody(
        @NotBlank
        @Size(min = 2, max = 30)
        String username,

        @NotBlank
        @Size(min = 2, max = 30)
        String password
) {
}
