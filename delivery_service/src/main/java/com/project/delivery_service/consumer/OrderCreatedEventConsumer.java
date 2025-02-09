package com.project.delivery_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.delivery_service.event.OrderCreatedEvent;
import com.project.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedEventConsumer {

    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "ORDER_CREATED", groupId = "${spring.kafka.consumer.group-id:delivery-service-group}")
    public void consume(String message) {
        try {
            log.info("Consumed message: {}", message);
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(message, OrderCreatedEvent.class);
            log.info("Order created event consumed: {}", orderCreatedEvent);
            deliveryService.receiveDelivery(orderCreatedEvent);
        } catch (Exception e) {
            log.error("Error Consume OrderCreatedEvent", e);
        }
    }
}
