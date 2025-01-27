package com.project.user_service.feign;

import com.project.user_service.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "order-service", url = "http://localhost:8082")
@FeignClient(name = "order-service")
public interface OrderApiService {

    @GetMapping("/{userId}/orders")
    ResponseEntity<List<ResponseOrder>> getOrdersByUserId(@PathVariable("userId") String userId);

}
