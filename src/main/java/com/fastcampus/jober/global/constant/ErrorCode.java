package com.fastcampus.jober.global.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    INVALID_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 id값입니다"),
    INVALID_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다"),
    INVALID_ACCESS(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 접근입니다"),
    INVALID_USER(HttpStatus.FORBIDDEN, "권한이 없습니다"),
    EMPTY_ID(HttpStatus.BAD_REQUEST, "아이디를 입력해주세요"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요"),
    EXISTING_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다"),
    CHECK_ID_PASSWORD(HttpStatus.BAD_REQUEST, "아이디와 비밀번호를 확인해주세요"),
    CHECK_ID(HttpStatus.BAD_REQUEST, "아이디를 확인해주세요"),
    CHECK_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요");

    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return httpStatus.value();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
