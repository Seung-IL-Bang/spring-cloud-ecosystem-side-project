package com.project.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCompletedEvent {

    private String orderId;
    private String paymentId;

}
