package com.sparta.order_service.order.service;

import com.sparta.order_service.global.exception.BusinessException;
import com.sparta.order_service.global.exception.ErrorCode;
import com.sparta.order_service.order.dto.OrderRequest;
import com.sparta.order_service.order.dto.OrderResponse;
import com.sparta.order_service.order.entity.Order;
import com.sparta.order_service.order.repository.OrderRepository;
import com.sparta.order_service.product.entity.Product;
import com.sparta.order_service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        int updated = productRepository.decreaseStock(request.getProductId(), request.getQuantity());
        if (updated == 0) {
            throw new BusinessException(ErrorCode.PRODUCT_OUT_OF_STOCK);
        }

        Order order = Order.builder()
                .product(product)
                .orderedPrice(product.getPrice())
                .quantity(request.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order).getId();
    }

    // 주문 조회 - 단건
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findByIdWithProduct(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponse.from(order);
    }

    // 주문 조회 - 목록
    public Page<OrderResponse> getOrders(Pageable pageable) {
        return orderRepository.findAllWithProduct(pageable)
                .map(OrderResponse::from);
    }
}
