package com.project.payment_service.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentApprovedEvent extends PaymentEvent {
    private BigDecimal payAmount;
}
