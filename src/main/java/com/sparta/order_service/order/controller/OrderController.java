package com.sparta.order_service.order.controller;

import com.sparta.order_service.global.ApiResponse;
import com.sparta.order_service.order.dto.OrderRequest;
import com.sparta.order_service.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ApiResponse<Long> createOrder(@RequestBody OrderRequest request) {
        Long orderId = orderService.createOrder(request);
        return ApiResponse.success(HttpStatus.CREATED, "주문 생성 성공", orderId);
    }

}
