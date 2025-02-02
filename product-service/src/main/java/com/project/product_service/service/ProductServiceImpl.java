package com.project.product_service.service;

import com.project.product_service.aop.stock.StockLock;
import com.project.product_service.entity.Product;
import com.project.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByProductId(String productId) {
        log.info("product-service: 상품 조회");
        Optional<Product> findProduct = productRepository.findByProductId(productId);

        if (findProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        return findProduct.get();
    }

    @Transactional
    @Override
    @StockLock
    public Product decreaseStock(String productId, Integer quantity) {
        log.info("product-service: 재고 차감");
        Optional<Product> findProduct = productRepository.findByProductId(productId);

        if (findProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = findProduct.get();
        product.decreaseStock(quantity);
        return product;
    }

    @Transactional
    @Override
    @StockLock
    public Product increaseStock(String productId, Integer quantity) {
        log.info("product-service: 재고 증가");
        Optional<Product> findProduct = productRepository.findByProductId(productId);

        if (findProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = findProduct.get();
        product.increaseStock(quantity);
        return product;
    }
}
