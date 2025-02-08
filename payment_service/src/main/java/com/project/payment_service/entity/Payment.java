package com.project.payment_service.entity;

import com.project.payment_service.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderId;

    @Column(unique = true)
    private String paymentId;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
