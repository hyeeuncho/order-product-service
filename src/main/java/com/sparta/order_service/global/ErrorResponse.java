package com.sparta.order_service.global;

import com.sparta.order_service.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}
