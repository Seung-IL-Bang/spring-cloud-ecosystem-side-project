package com.project.payment_service.entity.details;

import com.project.payment_service.entity.PaymentMethodMapping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public abstract class PaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_mapping_id", unique = true)
    private PaymentMethodMapping paymentMethodMapping;

    @Setter
    private String transactionId;

}
