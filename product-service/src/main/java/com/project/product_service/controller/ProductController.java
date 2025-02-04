package com.project.product_service.controller;

import com.project.product_service.entity.Product;
import com.project.product_service.service.ProductService;
import com.project.product_service.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final Environment env;

    @GetMapping("/test/config")
    public ResponseEntity<String> getConfig() {
        String testValue = env.getProperty("test.value");
        log.info("testValue : {}", testValue);
        return ResponseEntity.ok(testValue);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ResponseProduct>> getProducts() {
        List<Product> products = productService.getAllProducts();

        List<ResponseProduct> result = products
                .stream()
                .map(product -> modelMapper.map(product, ResponseProduct.class))
                .toList();

        log.info("products : {}", result);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/products/{productId}/stock")
    public Integer getStockByProductId(@PathVariable("productId") String productId) {
        Product product = productService.getProductByProductId(productId);
        log.info("product : {}", product);
        return product.getStock();
    }

    @PostMapping("/products/{productId}/order")
    public ResponseEntity<ResponseProduct> orderProduct(@PathVariable("productId") String productId,
                                                        @RequestParam Integer quantity) {
        Product product = productService.decreaseStock(productId, quantity);
        return ResponseEntity.ok(modelMapper.map(product, ResponseProduct.class));
    }

    @PostMapping("/products/{productId}/order-cancel")
    public ResponseEntity<ResponseProduct> orderCancelProduct(@PathVariable("productId") String productId,
                                                              @RequestParam Integer quantity) {
        Product product = productService.increaseStock(productId, quantity);
        return ResponseEntity.ok(modelMapper.map(product, ResponseProduct.class));
    }
}
