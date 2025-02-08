package com.project.payment_service.vo;

import lombok.Data;

@Data
public class OrderCancelledEvent implements Event {

    private String orderId;
    private String paymentId;

}
