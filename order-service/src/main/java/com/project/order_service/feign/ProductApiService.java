package com.project.order_service.feign;

import com.project.order_service.vo.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "product-service", url = "http://localhost:8081")
@FeignClient(name = "product-service")
public interface ProductApiService {

    @GetMapping("/products/{productId}")
    ResponseEntity<ResponseProduct> getProductByProductId(@PathVariable("productId") String productId);

    @PostMapping("/products/{productId}/order")
    ResponseEntity<ResponseProduct> checkStockAndOrder(@PathVariable("productId") String productId,
                                                       @RequestParam Integer quantity);

    @PostMapping("/products/{productId}/order-cancel")
    ResponseEntity<ResponseProduct> restoreStockAndOrderCancel(@PathVariable("productId") String productId,
                                                               @RequestParam Integer quantity);
}
