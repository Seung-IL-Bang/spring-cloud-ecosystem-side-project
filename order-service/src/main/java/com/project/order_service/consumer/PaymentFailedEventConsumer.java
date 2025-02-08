package com.project.order_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.order_service.event.PaymentFailedEvent;
import com.project.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.project.order_service.constant.OrderStatus.ORDER_CANCEL;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedEventConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "PAYMENT_FAILED", groupId = "${spring.kafka.consumer.group-id:order-service-group}")
    public void consume(String message) throws JsonProcessingException {
        try {
            log.info("Consumed message: {}", message);
            PaymentFailedEvent paymentFailedEvent = objectMapper.readValue(message, PaymentFailedEvent.class);
            log.info("Payment Failed event: {}", paymentFailedEvent);
            orderService.updateOrderStatus(paymentFailedEvent.getOrderId(), ORDER_CANCEL);
        } catch (Exception e) {
            log.error("Error Consume PaymentFailedEvent", e);
        }
    }
}
