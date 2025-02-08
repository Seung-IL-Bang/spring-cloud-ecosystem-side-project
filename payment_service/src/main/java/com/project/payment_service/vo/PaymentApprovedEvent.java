package com.project.payment_service.vo;

import com.project.payment_service.constant.PaymentStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentApprovedEvent extends PaymentEvent {

    public PaymentApprovedEvent(String orderId, String paymentId, PaymentStatus paymentStatus, BigDecimal paidTotalAmount) {
        super(orderId, paymentId, paymentStatus);
        this.paidTotalAmount = paidTotalAmount;
    }

    private final BigDecimal paidTotalAmount;
}
