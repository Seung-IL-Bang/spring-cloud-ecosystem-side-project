package com.project.order_service.service;

import com.project.order_service.dto.OrderDto;
import com.project.order_service.entity.Orders;
import com.project.order_service.feign.ProductApiService;
import com.project.order_service.feign.UserApiService;
import com.project.order_service.repository.OrderRepository;
import com.project.order_service.util.OrderIdGenerator;
import com.project.order_service.vo.ResponseProduct;
import com.project.order_service.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductApiService productApiService;
    private final UserApiService userApiService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("order-service: 주문 생성");
        ResponseEntity<ResponseUser> userResponse = userApiService.getUserByUserId(orderDto.getUserId());
        if (userResponse.getBody() == null) {
            throw new IllegalArgumentException("User not found");
        }

        // >>> 분산락 범위 적용 (재고 확인 -> 재고 차감)
        ResponseEntity<ResponseProduct> productResponse = productApiService
                .checkStockAndOrder(orderDto.getProductId(), orderDto.getQuantity());
        // <<< 분산락 범위 적용
        if (productResponse.getBody() == null) {
            throw new IllegalArgumentException("Product not found");
        }

        try {
            Integer unitPrice = productResponse.getBody().getUnitPrice();
            Orders order = Orders.builder()
                    .orderId(OrderIdGenerator.get())
                    .productId(orderDto.getProductId())
                    .userId(orderDto.getUserId())
                    .quantity(orderDto.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(orderDto.getQuantity() * unitPrice)
                    .createdAt(LocalDateTime.now())
                    .build();
            orderRepository.save(order);
            return modelMapper.map(order, OrderDto.class);
        } catch (Exception e) {
            productApiService.restoreStockAndOrderCancel(orderDto.getProductId(), orderDto.getQuantity());
            throw new IllegalArgumentException("Order create failed", e);
        }
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Orders order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<Orders> getOrdersByUserId(String userId) {
        log.info("order-service: 회원ID로 주문 목록 조회");
        return orderRepository.findByUserId(userId);
    }
}
