package com.project.product_service.service;

import com.project.product_service.entity.Product;
import com.project.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
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
        Optional<Product> findProduct = productRepository.findByProductId(productId);

        if (findProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        return findProduct.get();
    }
}
