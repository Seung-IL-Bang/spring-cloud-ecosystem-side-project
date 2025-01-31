package com.project.user_service.feign;

import com.project.user_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderClient {

    private final OrderApiService orderApiService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public List<ResponseOrder> getOrdersByUserId(String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        ResponseEntity<List<ResponseOrder>> orderResponse = circuitBreaker.run(
                () -> orderApiService.getOrdersByUserId(userId),
                throwable -> new ResponseEntity<>(new ArrayList<>(), null, HttpStatus.INTERNAL_SERVER_ERROR));
        return orderResponse.getBody();
    }

}
