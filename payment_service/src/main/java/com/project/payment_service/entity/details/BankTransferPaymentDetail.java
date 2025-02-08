package com.project.payment_service.entity.details;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "bank_transfer_payment_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankTransferPaymentDetail extends PaymentDetail {

    private String bankName;

    private String accountNumber;
}
