package com.project.payment_service.service.pg;

import com.project.payment_service.constant.PaymentMethodTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentGatewayFactory {

    private final List<PaymentGateway> paymentGateways;

    public PaymentGateway getPaymentGateway(PaymentMethodTypes paymentMethodType) {
        return paymentGateways.stream()
                .filter(paymentGateway -> paymentGateway.supports(paymentMethodType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported payment method"));
    }
}
