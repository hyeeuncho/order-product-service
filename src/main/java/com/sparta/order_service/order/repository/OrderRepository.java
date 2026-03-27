package com.sparta.order_service.order.repository;

import com.sparta.order_service.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 주문 단건 조회
    @Query("select o from Order o join fetch o.product where o.id = :orderId")
    Optional<Order> findByIdWithProduct(Long orderId);

    // 주문 목록 조회(페이지네이션)
    @Query(
            value = "select o from Order o join fetch o.product",
            countQuery = "select count(o) from Order o"
    )
    Page<Order> findAllWithProduct(Pageable pageable);
}
