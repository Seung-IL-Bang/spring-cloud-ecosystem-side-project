package com.project.payment_service.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class OrderCreatedEvent implements Event {

    private String orderId;
    private BigDecimal totalAmount;
    private List<PaymentInfo> paymentInfos;

    @Data
    public static class PaymentInfo {
        private String paymentMethod;
        private BigDecimal amount;
    }
}
