package com.project.payment_service.service.factory;

import com.project.payment_service.constant.PaymentMethodTypes;
import com.project.payment_service.entity.details.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentDetailFactory {

    public PaymentDetail createPaymentDetail(PaymentMethodTypes paymentMethodType) {
        return switch (paymentMethodType) {
            case CREDIT_CARD -> createCreditCardPaymentDetail();
            case BANK_TRANSFER -> createBankTransferPaymentDetail();
            case MOBILE_PAYMENT -> createMobilePaymentDetail();
            case COUPON -> createCouponPaymentDetail();
            case POINTS -> createPointsPaymentDetail();
            default -> throw new IllegalArgumentException("Unsupported payment method");
        };
    }

    private CreditCardPaymentDetail createCreditCardPaymentDetail() {
        CreditCardPaymentDetail creditCardPaymentDetail = CreditCardPaymentDetail.builder()
                .creditCardNumber(new CreditCardNumber("1234-5678-1234-5678"))
                .cardHolderName("John Doe")
                .expiryMonth("12")
                .expiryYear("2026")
                .cardBrand("VISA")
                .build();
        creditCardPaymentDetail.setTransactionId(UUID.randomUUID().toString());
        return creditCardPaymentDetail;
    }

    private BankTransferPaymentDetail createBankTransferPaymentDetail() {
        BankTransferPaymentDetail bankTransferPaymentDetail = BankTransferPaymentDetail.builder()
                .bankName("Bank of America")
                .accountNumber("1234-567890")
                .build();
        bankTransferPaymentDetail.setTransactionId(UUID.randomUUID().toString());
        return bankTransferPaymentDetail;
    }

    private MobilePaymentDetail createMobilePaymentDetail() {
        MobilePaymentDetail mobilePaymentDetail = MobilePaymentDetail.builder()
                .phoneNumber("010-1234-5678")
                .carrierName("SKT")
                .build();
        mobilePaymentDetail.setTransactionId(UUID.randomUUID().toString());
        return mobilePaymentDetail;
    }

    private CouponPaymentDetail createCouponPaymentDetail() {
        CouponPaymentDetail couponPaymentDetail = CouponPaymentDetail.builder()
                .couponId(1234)
                .couponCode("COUPON-1234")
                .build();
        couponPaymentDetail.setTransactionId(UUID.randomUUID().toString());
        return couponPaymentDetail;
    }

    private PointsPaymentDetail createPointsPaymentDetail() {
        PointsPaymentDetail pointsPaymentDetail = PointsPaymentDetail.builder()
                .userId("user-id-1234")
                .remainingPoints(BigDecimal.valueOf(1000))
                .build();
        pointsPaymentDetail.setTransactionId(UUID.randomUUID().toString());
        return pointsPaymentDetail;
    }
}

