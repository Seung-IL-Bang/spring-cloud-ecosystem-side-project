package com.project.product_service.service;

import com.project.product_service.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductByProductId(String productId);
}
