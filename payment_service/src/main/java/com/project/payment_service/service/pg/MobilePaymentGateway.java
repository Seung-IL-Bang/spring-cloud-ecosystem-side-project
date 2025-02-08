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
public class MobilePaymentGateway implements PaymentGateway{

    private final PaymentDetailFactory paymentDetailFactory;

    @Override
    public PaymentDetail pay(BigDecimal amount) {
        try {
            log.info("Processing mobile payment for amount: {}", amount);
            TimeUnit.SECONDS.sleep(1); // simulation delay
            log.info("Mobile payment successful for amount: {}", amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (Math.random() < 0.2) { // 20% 확률로 결제 실패
            throw new RuntimeException("Mobile payment failed");
        }

        return paymentDetailFactory.createPaymentDetail(PaymentMethodTypes.MOBILE_PAYMENT);
    }

    @Override
    public void cancel(String transactionId, BigDecimal amount) {
        log.info("Processing mobile payment cancellation for transactionId: {}, amount: {}", transactionId, amount);
    }

    @Override
    public boolean supports(PaymentMethodTypes paymentMethodType) {
        return PaymentMethodTypes.MOBILE_PAYMENT.equals(paymentMethodType);
    }

    @Override
    public boolean supportsCompensate() {
        return true;
    }
}
