package com.project.delivery_service.service;

import com.project.delivery_service.entity.AddressInfo;
import com.project.delivery_service.entity.Delivery;
import com.project.delivery_service.event.OrderCancelledEvent;
import com.project.delivery_service.event.OrderCompletedEvent;
import com.project.delivery_service.event.OrderCreatedEvent;
import com.project.delivery_service.producer.DeliveryEventProducer;
import com.project.delivery_service.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.delivery_service.constant.DeliveryEventTopics.*;
import static com.project.delivery_service.constant.DeliveryStatus.*;
import static com.project.delivery_service.constant.DeliveryStatus.PREPARING;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryEventProducer deliveryEventProducer;

    @Transactional
    public void receiveDelivery(OrderCreatedEvent orderCreatedEvent) {
        log.info("Receiving delivery for order: {}", orderCreatedEvent.getOrderId());

        OrderCreatedEvent.DeliveryInfo deliveryInfo = orderCreatedEvent.getDeliveryInfo();
        AddressInfo addressInfo = deliveryInfo.toAddressInfo();
        String recipientName = deliveryInfo.getRecipientName();
        String phoneNumber = deliveryInfo.getPhoneNumber();

        Delivery delivery = Delivery.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .deliveryId(UUID.randomUUID().toString())
                .addressInfo(addressInfo)
                .deliveryStatus(RECEIVED)
                .recipientName(recipientName)
                .recipientPhone(phoneNumber)
                .build();
        deliveryRepository.save(delivery);

        deliveryEventProducer.send(DELIVERY_RECEIVED, delivery.getDeliveryId());
    }

    @Transactional
    public void scheduleDelivery(OrderCompletedEvent orderCompletedEvent) {
        log.info("Scheduling delivery for order: {}", orderCompletedEvent.getOrderId());

        String orderId = orderCompletedEvent.getOrderId();

        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found for order: " + orderId));

        delivery.updateTrackingInfo("CJ Korea Shipping Company", "123456-7890", LocalDateTime.now().plusDays(1));
        delivery.updateStatus(PREPARING);
    }

    @Transactional
    public void startDelivery(String trackingNumber) {
        // 배송 시작일, 배송 상태 업데이트
        deliveryRepository.findByTrackingNumber(trackingNumber)
                .ifPresent(delivery -> {
                    delivery.updateStatus(IN_TRANSIT);
                    delivery.startDelivery(LocalDateTime.now());
                    deliveryEventProducer.send(DELIVERY_STARTED, trackingNumber);
                });
    }

    @Transactional
    public void completeDelivery(String trackingNumber) {
        // 배송 완료일, 배송 상태 업데이트
        deliveryRepository.findByTrackingNumber(trackingNumber)
                .ifPresent(delivery -> {
                    delivery.updateStatus(DELIVERED);
                    delivery.completeDelivery(LocalDateTime.now());
                });
    }

    @Transactional
    public void cancelDelivery(OrderCancelledEvent orderCancelledEvent) {
        log.info("Cancelling delivery for order: {}", orderCancelledEvent.getOrderId());

        String orderId = orderCancelledEvent.getOrderId();

        log.info("Cancelling delivery API to Carrier for order: {}", orderId);

        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found for order: " + orderId));

        delivery.updateStatus(CANCELLED);
        delivery.stopDelivery(LocalDateTime.now());
        deliveryEventProducer.send(DELIVERY_CANCELLED, delivery.getDeliveryId());
    }
}
