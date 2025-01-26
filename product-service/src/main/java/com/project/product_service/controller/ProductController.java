package com.project.product_service.controller;

import com.project.product_service.entity.Product;
import com.project.product_service.service.ProductService;
import com.project.product_service.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

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

    @GetMapping("/products/{productId}")
    public Integer getStockByProductId(@PathVariable("productId") String productId) {
        Product product = productService.getProductByProductId(productId);
        log.info("product : {}", product);
        return product.getStock();
    }
}
