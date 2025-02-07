package com.project.payment_service.constant;

public enum PaymentEventTopics {

    PAYMENT_APPROVED("결제 승인"),
    PAYMENT_FAILED("결제 실패"),
    PAYMENT_CANCELLED("결제 취소");


    private String event;

    PaymentEventTopics(String event) {
        this.event = event;
    }
}
