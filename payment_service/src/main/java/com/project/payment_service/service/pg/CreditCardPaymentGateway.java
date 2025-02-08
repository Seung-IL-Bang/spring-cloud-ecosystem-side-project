package com.project.payment_service.service.pg;

import com.project.payment_service.constant.PaymentMethodTypes;
import com.project.payment_service.entity.details.PaymentDetail;
import com.project.payment_service.service.factory.PaymentDetailFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditCardPaymentGateway implements PaymentGateway {

    private final PaymentDetailFactory paymentDetailFactory;

    @Override
    public PaymentDetail pay(BigDecimal amount) {

        try {
            log.info("Processing credit card payment for amount: {}", amount);
            TimeUnit.SECONDS.sleep(1); // simulation delay
            // todo pg api call -> pg response
            log.info("Credit card payment successful for amount: {}", amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (Math.random() < 1) { // 20% 확률로 결제 실패
            throw new RuntimeException("Credit card payment failed");
        }

        // todo pg response -> entity mapping
        return paymentDetailFactory.createPaymentDetail(PaymentMethodTypes.CREDIT_CARD); // dummy implementation
    }

    @Override
    public void cancel(String transactionId, BigDecimal amount) {
        log.info("Processing credit card payment cancellation for transactionId: {}, amount: {}", transactionId, amount);
    }

    @Override
    public boolean supports(PaymentMethodTypes paymentMethodType) {
        return PaymentMethodTypes.CREDIT_CARD.equals(paymentMethodType);
    }

    @Override
    public boolean supportsCompensate() {
        return true;
    }
}
