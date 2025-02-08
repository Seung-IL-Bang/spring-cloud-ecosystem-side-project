package com.project.order_service.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestCancelOrder {

    @NotNull
    private String orderId;

    @NotNull
    private String productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private String paymentId;

}
