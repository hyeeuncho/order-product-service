package com.sparta.order_service.order.controller;

import com.sparta.order_service.global.ApiResponse;
import com.sparta.order_service.order.dto.OrderRequest;
import com.sparta.order_service.order.dto.OrderResponse;
import com.sparta.order_service.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    // 주문 조회 - 단건
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.getOrder(orderId);
        return ApiResponse.success(HttpStatus.OK, "주문 조회 성공", response);
    }
}
