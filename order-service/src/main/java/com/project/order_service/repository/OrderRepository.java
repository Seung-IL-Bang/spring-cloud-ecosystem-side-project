package com.project.order_service.repository;

import com.project.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    Optional<Orders> findByOrderId(String orderId);

    List<Orders> findByUserId(String userId);
}
