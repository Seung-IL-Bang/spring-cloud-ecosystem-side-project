package com.project.order_service.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreatedEvent {

    private String orderId;
    private BigDecimal totalAmount;
    private List<PaymentInfo> paymentInfos;
    private DeliveryInfo deliveryInfo;

}
