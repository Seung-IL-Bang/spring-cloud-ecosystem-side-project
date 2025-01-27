package com.project.user_service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseOrder {
    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
