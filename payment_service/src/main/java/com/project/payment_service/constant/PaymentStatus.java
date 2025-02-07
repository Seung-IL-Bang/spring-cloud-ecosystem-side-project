package com.project.payment_service.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentStatus {

    APPROVED("결제 승인"),
    FAILED("결제 실패"),
    CANCELLED("결제 취소");

    private String description;

    PaymentStatus(String description) {
        this.description = description;
    }
}
