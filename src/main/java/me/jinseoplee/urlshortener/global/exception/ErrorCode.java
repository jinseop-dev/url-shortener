package me.jinseoplee.urlshortener.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(
            HttpStatus.BAD_REQUEST,
            "COMMON_001",
            "유효하지 않은 입력값입니다."
    ),

    // Server
    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "SERVER_001",
            "서버 내부 오류가 발생했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
