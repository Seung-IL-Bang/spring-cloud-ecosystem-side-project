package com.project.payment_service.vo;

import com.project.payment_service.constant.PaymentStatus;
import lombok.Getter;

@Getter
public class PaymentRefundedEvent extends PaymentEvent{
    private final String refundedReason;

    public PaymentRefundedEvent(String orderId, String paymentId, PaymentStatus paymentStatus, String refundedReason) {
        super(orderId, paymentId, paymentStatus);
        this.refundedReason = refundedReason;
    }
}
