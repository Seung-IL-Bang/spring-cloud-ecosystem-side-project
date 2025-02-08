package com.project.payment_service.repository;

import com.project.payment_service.entity.PaymentMethodMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodMappingRepository extends JpaRepository<PaymentMethodMapping, Integer> {

    List<PaymentMethodMapping> findByPaymentId(String paymentId);

}
