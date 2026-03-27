package com.sparta.order_service.product.repository;

import com.sparta.order_service.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 조회(삭제 안된것들만)
    Optional<Product> findByIdAndDeletedFalse(Long id);
    List<Product> findAllByDeletedFalse();
}
