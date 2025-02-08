package com.project.order_service.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInfo {
    private String paymentMethod;
    private BigDecimal amount;
}
