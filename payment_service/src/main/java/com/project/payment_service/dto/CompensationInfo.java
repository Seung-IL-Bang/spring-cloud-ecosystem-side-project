package com.project.payment_service.dto;

import com.project.payment_service.service.pg.PaymentGateway;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CompensationInfo {

    private final PaymentGateway paymentGateway;
    private final String transactionId;
    private final BigDecimal amount;

    public CompensationInfo(PaymentGateway paymentGateway, String transactionId, BigDecimal amount) {
        this.paymentGateway = paymentGateway;
        this.transactionId = transactionId;
        this.amount = amount;
    }

}
