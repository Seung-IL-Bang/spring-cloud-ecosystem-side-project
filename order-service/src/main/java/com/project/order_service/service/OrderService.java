package com.project.order_service.service;

import com.project.order_service.constant.OrderStatus;
import com.project.order_service.dto.OrderDto;
import com.project.order_service.entity.Orders;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    void cancelOrder(OrderDto orderDto);

    OrderDto getOrderByOrderId(String orderId);

    List<Orders> getOrdersByUserId(String userId);

    void updateOrderStatus(String orderId, OrderStatus status);

}
