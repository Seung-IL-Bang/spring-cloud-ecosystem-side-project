package com.project.order_service.dto;

import com.project.order_service.event.DeliveryInfo;
import com.project.order_service.event.PaymentInfo;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String productId;
    private String orderId;
    private String paymentId;
    private String userId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private List<PaymentInfo> paymentInfos;
    private DeliveryInfo deliveryInfo;

}
