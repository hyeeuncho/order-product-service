package com.sparta.order_service.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "해당 상품이 존재하지 않습니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.CONFLICT, "P002", "재고가 부족합니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "해당 주문이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
