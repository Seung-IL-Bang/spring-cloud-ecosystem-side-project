package com.project.payment_service.entity.details;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "points_payment_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointsPaymentDetail extends PaymentDetail {

    private String userId;
    private BigDecimal remainingPoints;

}
