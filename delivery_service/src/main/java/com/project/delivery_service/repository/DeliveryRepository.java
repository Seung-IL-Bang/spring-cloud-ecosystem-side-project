package com.project.delivery_service.repository;

import com.project.delivery_service.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Optional<Delivery> findByOrderId(String orderId);

    Optional<Delivery> findByTrackingNumber(String trackingNumber);
}
