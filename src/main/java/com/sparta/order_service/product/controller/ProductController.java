package com.sparta.order_service.product.controller;

import com.sparta.order_service.global.ApiResponse;
import com.sparta.order_service.product.dto.ProductRequest;
import com.sparta.order_service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ApiResponse<Long> createProduct(@RequestBody ProductRequest request) {
        Long productId = productService.createProduct(request);
        return ApiResponse.success(HttpStatus.CREATED, "상품 등록 성공", productId);
    }
}
