package com.project.payment_service.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreatedEvent implements Event {

    private String orderId;
    private BigDecimal totalAmount;
    private List<PaymentInfo> paymentInfos;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentInfo {
        private String paymentMethod;
        private BigDecimal amount;
    }
}
