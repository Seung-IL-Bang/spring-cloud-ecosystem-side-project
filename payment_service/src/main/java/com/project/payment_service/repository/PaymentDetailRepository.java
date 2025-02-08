package com.project.payment_service.repository;

import com.project.payment_service.entity.details.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Integer> {
}
