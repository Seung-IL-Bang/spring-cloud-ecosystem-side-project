package com.project.payment_service.vo;

import com.project.payment_service.constant.PaymentStatus;
import lombok.Getter;

@Getter
public class PaymentFailedEvent extends PaymentEvent {

    private final String failedReason;

    public PaymentFailedEvent(String orderId, String paymentId, PaymentStatus paymentStatus, String failedReason) {
        super(orderId, paymentId, paymentStatus);
        this.failedReason = failedReason;
    }
}
