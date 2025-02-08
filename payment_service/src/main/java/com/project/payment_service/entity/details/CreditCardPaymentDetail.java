package com.project.payment_service.entity.details;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "credit_card_payment_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCardPaymentDetail extends PaymentDetail {

    @Embedded
    private CreditCardNumber creditCardNumber;

    private String cardHolderName;

    private String expiryMonth;

    private String expiryYear;

    private String cardBrand;

    public String getCardNumber() {
        return creditCardNumber.getCardNumber();
    }
}
