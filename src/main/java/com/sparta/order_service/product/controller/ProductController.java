package com.sparta.order_service.product.controller;

import com.sparta.order_service.global.ApiResponse;
import com.sparta.order_service.product.dto.ProductRequest;
import com.sparta.order_service.product.dto.ProductResponse;
import com.sparta.order_service.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 상품 조회 - 단건
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProduct(productId);
        return ApiResponse.success(HttpStatus.OK, "상품 단건 조회 성공", response);
    }

    // 상품 조회 - 목록
    @GetMapping
    public ApiResponse<List<ProductResponse>> getProducts() {
        List<ProductResponse> response = productService.getProducts();
        return ApiResponse.success(HttpStatus.OK, "상품 목록 조회 성공", response);
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ApiResponse<Long> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequest request
    ) {
        Long updatedProductId = productService.updateProduct(productId, request);
        return ApiResponse.success(HttpStatus.OK, "상품 수정 성공", updatedProductId);
    }
}
