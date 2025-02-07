package com.project.payment_service.service;

import com.project.payment_service.producer.PaymentEventProducer;
import com.project.payment_service.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.project.payment_service.constant.PaymentEventTopics.*;
import static com.project.payment_service.constant.PaymentStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentEventProducer paymentEventProducer;

    @Override
    public void process(OrderCreatedEvent event) {
        log.info("Payment processed for order: {}", event.getOrderId());

        if (Math.random() < 0.8) {
            log.info("Payment successful for order: {}", event.getOrderId());

            PaymentApprovedEvent paymentApprovedEvent = new PaymentApprovedEvent();
            paymentApprovedEvent.setOrderId(event.getOrderId());
            paymentApprovedEvent.setPayAmount(BigDecimal.valueOf(Math.random()));
            paymentApprovedEvent.setPaymentStatus(APPROVED);
            paymentApprovedEvent.setPaymentId(UUID.randomUUID().toString());

            paymentEventProducer.send(PAYMENT_APPROVED, paymentApprovedEvent);
        } else {
            log.info("Payment failed for order: {}", event.getOrderId());

            PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent();
            paymentFailedEvent.setOrderId(event.getOrderId());
            paymentFailedEvent.setPaymentStatus(FAILED);
            paymentFailedEvent.setPaymentId(UUID.randomUUID().toString());
            paymentFailedEvent.setFailedReason("Payment Error Reason");

            paymentEventProducer.send(PAYMENT_FAILED, paymentFailedEvent);
        }
    }

    @Override
    public void refund(OrderCancelledEvent event) {
        log.info("Payment refunded for order: {}", event.getOrderId());

        PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();
        paymentCancelledEvent.setOrderId(event.getOrderId());
        paymentCancelledEvent.setPaymentId(event.getPaymentId());
        paymentCancelledEvent.setPaymentStatus(CANCELLED);
        paymentCancelledEvent.setCancelledReason("Payment Cancelled Reason");

        paymentEventProducer.send(PAYMENT_CANCELLED, paymentCancelledEvent);
    }
}
