package com.project.payment_service.repository;

import com.project.payment_service.constant.PaymentMethodTypes;
import com.project.payment_service.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    Optional<PaymentMethod> findByPaymentMethodType(PaymentMethodTypes paymentMethodType);
}
