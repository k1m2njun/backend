package com.fastcampus.jober.core.exception;

import com.fastcampus.jober.core.dto.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 권한 없음
@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        return new ResponseDTO<>(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase(), getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}