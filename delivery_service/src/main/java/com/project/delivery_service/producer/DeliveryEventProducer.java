package com.project.delivery_service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.delivery_service.constant.DeliveryEventTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public <T> void send(DeliveryEventTopics topic, T event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            log.info("Producing message: {}", message);
            kafkaTemplate.send(topic.name(), message);
        } catch (Exception e) {
            log.error("Error converting DeliveryEvent", e);
            throw new IllegalArgumentException(e);
        }
    }
}
