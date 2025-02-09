package com.project.delivery_service.controller;

import com.project.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/web-hook/delivery/start/{trackingNumber}")
    public ResponseEntity<String> startDelivery(@PathVariable("trackingNumber") String trackingNumber) {
        log.info("Starting delivery: {}", trackingNumber);
        deliveryService.startDelivery(trackingNumber);
        return ResponseEntity.ok("Delivery started: " + trackingNumber);
    }

    @PostMapping("/web-hook/delivery/complete/{trackingNumber}")
    public ResponseEntity<String> completeDelivery(@PathVariable("trackingNumber") String trackingNumber) {
        log.info("Completing delivery: {}", trackingNumber);
        deliveryService.completeDelivery(trackingNumber);
        return ResponseEntity.ok("Delivery completed: " + trackingNumber);
    }
}
