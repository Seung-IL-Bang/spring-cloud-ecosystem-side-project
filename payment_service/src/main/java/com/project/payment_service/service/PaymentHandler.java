package com.project.payment_service.service;

import com.project.payment_service.constant.PaymentStatus;
import com.project.payment_service.producer.PaymentEventProducer;
import com.project.payment_service.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.project.payment_service.constant.PaymentEventTopics.*;
import static com.project.payment_service.constant.PaymentStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentHandler {

    private final PaymentProcessFacade paymentProcessFacade;
    private final PaymentEventProducer paymentEventProducer;

    public void handle(Event event) {
        try {
            handleEvent(event);
        } catch (Exception e) {
            handleException(event, e);
        }
    }

    private void handleEvent(Event event) {
        if (event instanceof OrderCreatedEvent orderCreatedEvent) {
            pay(orderCreatedEvent);
        } else if (event instanceof OrderCancelledEvent orderCancelledEvent) {
            refund(orderCancelledEvent);
        } else {
            throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getName());
        }
    }

    private void handleException(Event event, Exception e) {
        log.error("Error handling payment", e);
        if (event instanceof OrderCreatedEvent orderCreatedEvent) {
            paymentEventProducer.send(PAYMENT_FAILED, new PaymentFailedEvent(
                    orderCreatedEvent.getOrderId(),
                    null,
                    FAILED, e.getMessage()));
        } else if (event instanceof OrderCancelledEvent orderCancelledEvent) {
            paymentEventProducer.send(PAYMENT_REFUND_FAILED, new PaymentFailedEvent(
                    orderCancelledEvent.getOrderId(),
                    orderCancelledEvent.getPaymentId(),
                    REFUND_FAILED, e.getMessage()));
        }
    }

    private void pay(OrderCreatedEvent event) {
        PaymentEvent paymentEvent = paymentProcessFacade.processPay(event);
        paymentEventProducer.send(PAYMENT_APPROVED, paymentEvent); // approved event
    }

    private void refund(OrderCancelledEvent event) {
        PaymentEvent paymentEvent = paymentProcessFacade.processRefund(event);
        paymentEventProducer.send(PAYMENT_REFUND, paymentEvent); // cancelled event
    }
}
