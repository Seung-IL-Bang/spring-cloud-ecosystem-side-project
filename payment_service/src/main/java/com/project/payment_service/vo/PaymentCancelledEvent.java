package com.project.payment_service.vo;

import lombok.Data;

@Data
public class PaymentCancelledEvent extends PaymentEvent{
    private String cancelledReason;
}
