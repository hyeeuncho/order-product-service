package com.sparta.order_service.product.service;

import com.sparta.order_service.global.exception.BusinessException;
import com.sparta.order_service.global.exception.ErrorCode;
import com.sparta.order_service.product.dto.ProductRequest;
import com.sparta.order_service.product.dto.ProductResponse;
import com.sparta.order_service.product.entity.Product;
import com.sparta.order_service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
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
                .stock(request.getStock())
                .build();
        return productRepository.save(product).getId();
    }

    // 상품 조회 - 단건
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponse.from(product);
    }

    // 상품 조회 - 목록
    public List<ProductResponse> getProducts() {
        return productRepository.findAllByDeletedFalse().stream()
                .map(ProductResponse::from)
                .toList();
    }

    // 상품 수정
    @Transactional
    public Long updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        product.update(
                request.getName(),
                request.getPrice(),
                request.getDescription()
        );

        return product.getId();
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        product.delete();
    }
}
