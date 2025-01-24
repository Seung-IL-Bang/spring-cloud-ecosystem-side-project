package com.project.user_service.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseOrder {
    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDate createdAt;
}
