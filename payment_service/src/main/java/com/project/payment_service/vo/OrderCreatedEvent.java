package com.project.payment_service.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderCreatedEvent {

    private String orderId;

}
