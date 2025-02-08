package com.project.product_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.product_service.event.OrderCancelledEvent;
import com.project.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCancelledEventConsumer {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ORDER_CANCELLED", groupId = "${spring.kafka.consumer.group-id:product-service-group}")
    public void consume(String message) throws JsonProcessingException {
        try {
            log.info("Consumed message: {}", message);
            OrderCancelledEvent orderCancelledEvent = objectMapper.readValue(message, OrderCancelledEvent.class);
            log.info("Order cancelled event consumed: {}", orderCancelledEvent);

            productService.increaseStock(orderCancelledEvent.getProductId(), orderCancelledEvent.getQuantity());
        } catch (Exception e) {
            log.error("Error Consume OrderCancelledEvent", e);
        }
    }
}
