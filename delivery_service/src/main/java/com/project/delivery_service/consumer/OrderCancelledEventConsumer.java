package com.project.delivery_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.delivery_service.event.OrderCancelledEvent;
import com.project.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCancelledEventConsumer {

    private final ObjectMapper objectMapper;
    private final DeliveryService deliveryService;

    @KafkaListener(topics = "ORDER_CANCELLED", groupId = "${spring.kafka.consumer.group-id:delivery-service-group}")
    public void consume(String message) {
        try {
            log.info("Consumed message: {}", message);
            OrderCancelledEvent orderCancelledEvent = objectMapper.readValue(message, OrderCancelledEvent.class);
            log.info("Order cancelled event consumed: {}", orderCancelledEvent);
            deliveryService.cancelDelivery(orderCancelledEvent);
        } catch (Exception e) {
            log.error("Error Consume OrderCancelledEvent", e);
        }
    }
}
