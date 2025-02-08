package com.project.payment_service.service.pg;

import com.project.payment_service.constant.PaymentMethodTypes;
import com.project.payment_service.entity.details.PaymentDetail;

import java.math.BigDecimal;

public interface PaymentGateway {

    PaymentDetail pay(BigDecimal amount);

    void cancel(String transactionId, BigDecimal amount);

    boolean supports(PaymentMethodTypes paymentMethodType);

    boolean supportsCompensate();

}
