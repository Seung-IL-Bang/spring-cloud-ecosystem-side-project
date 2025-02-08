package com.project.order_service.vo;

import com.project.order_service.event.PaymentInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestOrder {

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;

    @NotEmpty
    private List<PaymentInfo> paymentInfos;
}
