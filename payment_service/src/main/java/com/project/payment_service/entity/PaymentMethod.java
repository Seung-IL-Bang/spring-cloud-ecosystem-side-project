package com.project.payment_service.entity;

import com.project.payment_service.constant.PaymentMethodTypes;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "payment_method")
@Getter
public class PaymentMethod extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private PaymentMethodTypes paymentMethodType;

    private String description;

}
