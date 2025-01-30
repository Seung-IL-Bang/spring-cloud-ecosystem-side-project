package com.project.product_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    void decreaseStock() {

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        Runnable task = () -> {
            productService.decreaseStock("CATALOG-0001", 1);
        };

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(executorService.submit(task));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
    }

}