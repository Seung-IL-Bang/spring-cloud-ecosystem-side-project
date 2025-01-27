package com.project.order_service.dto;

import lombok.Data;

@Data
public class OrderDto {

    private String productId;
    private String orderId;
    private String userId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

}
