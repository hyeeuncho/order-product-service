package com.sparta.order_service.product.dto;

import com.sparta.order_service.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription()
        );
    }
}
