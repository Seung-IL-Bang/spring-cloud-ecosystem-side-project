package com.project.order_service.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class PaymentApprovedEvent {

    private String orderId;
    private String paymentId;
    private PaymentStatus paymentStatus;
    private final BigDecimal paidTotalAmount;

    @JsonCreator
    public PaymentApprovedEvent(
            @JsonProperty("orderId") String orderId,
            @JsonProperty("paymentId") String paymentId,
            @JsonProperty("paymentStatus") PaymentStatus paymentStatus,
            @JsonProperty("paidTotalAmount") BigDecimal paidTotalAmount) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.paidTotalAmount = paidTotalAmount;
    }

    @Data
    public static class PaymentStatus {
        private String description;
    }

}
