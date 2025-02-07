package com.project.payment_service.vo;

import lombok.Data;

@Data
public class OrderCancelledEvent {

    private String orderId;
    private String paymentId;

}
