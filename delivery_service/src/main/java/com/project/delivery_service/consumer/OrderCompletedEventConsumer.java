package com.project.delivery_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.delivery_service.event.OrderCompletedEvent;
import com.project.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCompletedEventConsumer {

    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "ORDER_COMPLETED", groupId = "${spring.kafka.consumer.group-id:delivery-service-group}")
    public void consume(String message) {
        try {
            log.info("Consumed message: {}", message);
            OrderCompletedEvent orderCompletedEvent = objectMapper.readValue(message, OrderCompletedEvent.class);
            log.info("Order completed event consumed: {}", orderCompletedEvent);
            deliveryService.scheduleDelivery(orderCompletedEvent);
        } catch (Exception e) {
            log.error("Error Consume OrderCompletedEvent", e);
        }
    }

}
