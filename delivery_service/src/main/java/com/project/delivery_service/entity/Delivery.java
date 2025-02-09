package com.project.delivery_service.entity;

import com.project.delivery_service.constant.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String deliveryId;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;

    @Column(name = "recipient_phone", length = 20)
    private String recipientPhone;

    @Embedded
    private AddressInfo addressInfo;

    @Column(name = "carrier", length = 100)
    private String carrier;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate; // 배송 예정일

    @Column(name = "started_date")
    private LocalDateTime startedDate; // 배송 시작일

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate; // 배송 완료일

    @Column(name = "stopped_date")
    private LocalDateTime stoppedDate; // 배송 중지일

    public void updateStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void updateTrackingInfo(String carrier, String trackingNumber, LocalDateTime scheduledDate) {
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.scheduledDate = scheduledDate;
    }

    public void startDelivery(LocalDateTime startedDate) {
        this.startedDate = startedDate;
    }

    public void completeDelivery(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public void stopDelivery(LocalDateTime stoppedDate) {
        this.stoppedDate = stoppedDate;
    }

}
