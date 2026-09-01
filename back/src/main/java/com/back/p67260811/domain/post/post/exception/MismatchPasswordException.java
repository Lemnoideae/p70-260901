package com.back.p67260811.domain.post.post.exception;

public class MismatchPasswordException extends RuntimeException {
    public MismatchPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
