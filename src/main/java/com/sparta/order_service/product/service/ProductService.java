package com.sparta.order_service.product.service;

import com.sparta.order_service.product.dto.ProductRequest;
import com.sparta.order_service.product.dto.ProductResponse;
import com.sparta.order_service.product.entity.Product;
import com.sparta.order_service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 상품 등록
    @Transactional
    public Long createProduct(ProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();
        return productRepository.save(product).getId();
    }
}
