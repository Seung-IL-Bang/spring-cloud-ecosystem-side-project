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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductApiService productApiService;
    private final UserApiService userApiService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        // 존재하는 유저 ID 인지 확인
        // 존재하지 않는다면 404 에러 반환
        ResponseEntity<ResponseUser> userResponse = userApiService.getUserById(orderDto.getUserId());

        // 존재하는 상품 ID 인지 확인
        // 존재하지 않는다면 404 에러 반환

        // >>> ProductService 에서 하나의 과정으로 묶어야 할듯. 아니면 분산락 범위 적용
        // 재고 확인
        // 재고가 부족하다면 400 에러 반환
        ResponseEntity<ResponseProduct> productResponse = productApiService.getProductByProductId(orderDto.getProductId());
        // 상품 단가 조회

        if (productResponse.getBody() != null && productResponse.getBody().getStock() - orderDto.getQuantity() < 0) {
            throw new IllegalArgumentException("Out of stock");
        }

        // 재고 감소
        productApiService.decreaseStock(orderDto.getProductId(), orderDto.getQuantity());
        // <<< ProductService 에서 하나의 과정으로 묶어야 할듯. 아니면 분산락 범위 적용


        // 주문 생성
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

        // 주문 정보 저장
        orderRepository.save(order);

        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        return null;
    }

    @Override
    public List<Orders> getOrdersByUserId(String userId) {

        return null;
    }
}
