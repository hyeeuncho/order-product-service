package com.sparta.order_service.order.dto;

import com.sparta.order_service.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer orderedPrice;
    private LocalDateTime createdAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .orderedPrice(order.getOrderedPrice())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
