package com.project.product_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCancelledEvent {

    private String orderId;
    private String productId;
    private String paymentId;
    private Integer quantity;

}
