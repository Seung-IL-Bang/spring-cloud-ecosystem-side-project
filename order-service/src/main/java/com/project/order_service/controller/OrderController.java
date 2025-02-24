package com.project.order_service.controller;

import com.project.order_service.dto.OrderDto;
import com.project.order_service.entity.Orders;
import com.project.order_service.service.OrderService;
import com.project.order_service.vo.RequestCancelOrder;
import com.project.order_service.vo.RequestOrder;
import com.project.order_service.vo.ResponseOrder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final Environment env;

    @GetMapping("/test/config")
    public ResponseEntity<String> getConfig() {
        String testValue = env.getProperty("test.value");
        System.out.println("testValue = " + testValue);
        return ResponseEntity.ok(testValue);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody @Valid RequestOrder requestOrder) {
        OrderDto requestDto = modelMapper.map(requestOrder, OrderDto.class);
        requestDto.setUserId(userId);
        OrderDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(responseDto, ResponseOrder.class));
    }

    @DeleteMapping("/{userId}/orders")
    public ResponseEntity<String> cancelOrder(@PathVariable("userId") String userId,
                                              @RequestBody @Valid RequestCancelOrder requestCancelOrder) {
        OrderDto requestDto = modelMapper.map(requestCancelOrder, OrderDto.class);
        requestDto.setUserId(userId);
        orderService.cancelOrder(requestDto);
        return ResponseEntity.ok("Deleted all orders of user: " + userId);
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

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrderByOrderId(@PathVariable("orderId") String orderId) {
        OrderDto orderDto = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(modelMapper.map(orderDto, ResponseOrder.class));
    }

}
