package com.project.order_service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.order_service.event.OrderCompletedEvent;
import com.project.order_service.event.PaymentApprovedEvent;
import com.project.order_service.producer.OrderEventProducer;
import com.project.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.project.order_service.constant.OrderEventTopics.ORDER_COMPLETED;
import static com.project.order_service.constant.OrderStatus.COMPLETED;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentApprovedEventConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    private final OrderEventProducer orderEventProducer;

    @KafkaListener(topics = "PAYMENT_APPROVED", groupId = "${spring.kafka.consumer.group-id:order-service-group}")
    public void consume(String message) throws JsonProcessingException {
        try {
            log.info("Consumed message: {}", message);
            PaymentApprovedEvent paymentApprovedEvent = objectMapper.readValue(message, PaymentApprovedEvent.class);
            log.info("Payment approved event: {}", paymentApprovedEvent);
            orderService.updateOrderStatus(paymentApprovedEvent.getOrderId(), COMPLETED);
            orderEventProducer.send(ORDER_COMPLETED, new OrderCompletedEvent(paymentApprovedEvent.getOrderId(), paymentApprovedEvent.getPaymentId()));
        } catch (Exception e) {
            log.error("Error Consume PaymentApprovedEvent", e);
        }
    }
}
