package com.project.payment_service.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentMethodTypes {

    CREDIT_CARD("신용카드 결제"),
    BANK_TRANSFER("계좌이체"),
    MOBILE_PAYMENT("모바일 결제"),
    COUPON("쿠폰 할인 결제"),
    POINTS("포인트 결제");

    private String description;

    PaymentMethodTypes(String description) {
        this.description = description;
    }
}
