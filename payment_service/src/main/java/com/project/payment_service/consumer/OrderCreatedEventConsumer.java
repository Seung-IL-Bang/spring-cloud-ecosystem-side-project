package com.project.payment_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.payment_service.service.PaymentHandler;
import com.project.payment_service.vo.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedEventConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentHandler paymentHandler;

    @KafkaListener(topics = "ORDER_CREATED", groupId = "${spring.kafka.consumer.group-id:payment-service-group}")
    public void consume(String message) throws JsonProcessingException {
        log.info("Consumed message: {}", message);
        OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(message, OrderCreatedEvent.class);
        log.info("Order created event: {}", orderCreatedEvent);

        paymentHandler.handle(orderCreatedEvent); // todo 결제 처리 => failed event 발생 가능
    }

}
