package com.project.payment_service.entity.details;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "coupon_payment_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponPaymentDetail extends PaymentDetail {

    private Integer couponId;
    private String couponCode;
}
