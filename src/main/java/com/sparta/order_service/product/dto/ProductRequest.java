package com.sparta.order_service.product.dto;

import lombok.Getter;

@Getter
public class ProductRequest {
    private String name;
    private Integer price;
    private String description;
}
