package com.project.order_service.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentFailedEvent {

    private String orderId;
    private String paymentId;
    private PaymentApprovedEvent.PaymentStatus paymentStatus;
    private String failedReason;

    @JsonCreator
    public PaymentFailedEvent(
            @JsonProperty("orderId") String orderId,
            @JsonProperty("paymentId") String paymentId,
            @JsonProperty("paymentStatus") PaymentApprovedEvent.PaymentStatus paymentStatus,
            @JsonProperty("failedReason") String failedReason) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.failedReason = failedReason;
    }

    @Data
    public static class PaymentStatus {
        private String description;
    }

}
