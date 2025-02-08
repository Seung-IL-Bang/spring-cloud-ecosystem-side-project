package com.project.payment_service.vo;

import com.project.payment_service.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class PaymentEvent {

    private String orderId;
    private String paymentId;
    private PaymentStatus paymentStatus;

}
