package com.project.payment_service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.payment_service.constant.PaymentEventTopics;
import com.project.payment_service.vo.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(PaymentEventTopics topic, PaymentEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            log.info("Producing message: {}", message);
            kafkaTemplate.send(topic.name(), message);
        } catch (Exception e) {
            log.error("Error converting PaymentEvent", e);
            throw new IllegalArgumentException(e);
        }
    }

}
