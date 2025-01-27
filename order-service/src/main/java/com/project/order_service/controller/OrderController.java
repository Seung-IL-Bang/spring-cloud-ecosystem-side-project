package com.project.order_service.controller;

import com.project.order_service.dto.OrderDto;
import com.project.order_service.entity.Orders;
import com.project.order_service.service.OrderService;
import com.project.order_service.vo.RequestOrder;
import com.project.order_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder requestOrder) {
        OrderDto requestDto = modelMapper.map(requestOrder, OrderDto.class);
        requestDto.setUserId(userId);
        OrderDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(responseDto, ResponseOrder.class));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {

        List<Orders> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = orderList
                .stream()
                .map(order -> modelMapper.map(order, ResponseOrder.class))
                .toList();

        return ResponseEntity.ok(result);
    }

}
