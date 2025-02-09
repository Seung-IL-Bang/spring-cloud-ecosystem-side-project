package com.project.delivery_service.controller;

import com.project.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/web-hook/delivery/start/{trackingNumber}")
    public ResponseEntity<String> startDelivery(@PathVariable("trackingNumber") String trackingNumber) {
        deliveryService.startDelivery(trackingNumber);
        return ResponseEntity.ok("Delivery started: " + trackingNumber);
    }

    @PostMapping("/web-hook/delivery/complete/{trackingNumber}")
    public ResponseEntity<String> completeDelivery(@PathVariable("trackingNumber") String trackingNumber) {
        deliveryService.completeDelivery(trackingNumber);
        return ResponseEntity.ok("Delivery completed: " + trackingNumber);
    }
}
