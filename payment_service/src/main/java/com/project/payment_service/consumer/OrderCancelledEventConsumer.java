package com.project.payment_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.payment_service.service.PaymentHandler;
import com.project.payment_service.vo.OrderCancelledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCancelledEventConsumer {

    private final PaymentHandler paymentHandler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ORDER_CANCELLED", groupId = "${spring.kafka.consumer.group-id:payment-service-group}")
    public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        try {
            String message = record.value();
            log.info("Consumed message: {}", message);
            OrderCancelledEvent orderCancelledEvent = objectMapper.readValue(message, OrderCancelledEvent.class);
            log.info("Order cancelled event: {}", orderCancelledEvent);

            paymentHandler.handle(orderCancelledEvent);
        } catch (Exception e) {
            log.error("Error Consume OrderCancelledEvent", e);
        }
    }
}
