package com.project.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderCreatedEvent {

    private String orderId;
    private BigDecimal totalAmount;
    private List<PaymentInfo> paymentInfos;

}
