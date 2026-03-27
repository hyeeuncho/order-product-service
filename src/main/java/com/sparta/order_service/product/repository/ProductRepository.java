package com.sparta.order_service.product.repository;

import com.sparta.order_service.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
