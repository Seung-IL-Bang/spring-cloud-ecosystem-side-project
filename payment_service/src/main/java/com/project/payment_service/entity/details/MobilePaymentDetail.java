package com.project.payment_service.entity.details;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "mobile_payment_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobilePaymentDetail extends PaymentDetail {

    private String phoneNumber;

    private String carrierName;
}
