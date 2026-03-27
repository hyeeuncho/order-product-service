package com.sparta.order_service.order.service;

import com.sparta.order_service.order.dto.OrderRequest;
import com.sparta.order_service.order.entity.Order;
import com.sparta.order_service.order.repository.OrderRepository;
import com.sparta.order_service.product.entity.Product;
import com.sparta.order_service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Transactional
    public Long createOrder(OrderRequest request) {
        Product product = productRepository.findByIdAndDeletedFalse(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        Order order = Order.builder()
                .product(product)
                .orderedPrice(product.getPrice())
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order).getId();
    }
}
