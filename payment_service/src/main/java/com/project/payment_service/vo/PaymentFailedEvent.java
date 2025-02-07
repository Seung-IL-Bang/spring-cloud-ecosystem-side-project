package com.project.payment_service.vo;

import lombok.Data;

@Data
public class PaymentFailedEvent extends PaymentEvent {
    private String failedReason;
}
