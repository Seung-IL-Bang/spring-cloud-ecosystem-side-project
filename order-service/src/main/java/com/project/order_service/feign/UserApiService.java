package com.project.order_service.feign;

import com.project.order_service.vo.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "user-service", url = "http://localhost:8080")
@FeignClient(name = "user-service")
public interface UserApiService {

    @GetMapping("/users/{userId}")
    ResponseEntity<ResponseUser> getUserByUserId(@PathVariable("userId") String userId);

}
