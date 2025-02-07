package com.project.payment_service.service;

import com.project.payment_service.vo.OrderCancelledEvent;
import com.project.payment_service.vo.OrderCreatedEvent;

public interface PaymentService {

    void process(OrderCreatedEvent event);
    void refund(OrderCancelledEvent event);

}
