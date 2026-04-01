package com.sparta.order_service.product.repository;

import com.sparta.order_service.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 상품 조회(삭제 안된것들만)
    Optional<Product> findByIdAndDeletedFalse(Long id);
    List<Product> findAllByDeletedFalse();

    // 재고 원자적 차감 (재고 부족 시 0건 반환)
    @Modifying
    @Query("UPDATE Product p SET p.stock = p.stock - :quantity " +
           "WHERE p.id = :id AND p.deleted = false AND p.stock >= :quantity")
    int decreaseStock(@Param("id") Long id, @Param("quantity") int quantity);
}
