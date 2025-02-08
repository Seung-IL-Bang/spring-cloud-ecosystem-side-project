package com.project.payment_service.entity.details;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@Embeddable
public class CreditCardNumber {

    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");

    private String part1;
    private String part2;
    private String part3;
    private String part4;

    public CreditCardNumber() {}

    public CreditCardNumber(String cardNumber) {
        if (!CARD_NUMBER_PATTERN.matcher(cardNumber).matches()) {
            throw new IllegalArgumentException("Invalid credit card number format");
        }
        String[] parts = cardNumber.split("-");
        this.part1 = parts[0];
        this.part2 = parts[1];
        this.part3 = parts[2];
        this.part4 = parts[3];
    }

    public CreditCardNumber(String part1, String part2, String part3, String part4) {
        if (!part1.matches("\\d{4}") || !part2.matches("\\d{4}") || !part3.matches("\\d{4}") || !part4.matches("\\d{4}")) {
            throw new IllegalArgumentException("Each part of the credit card number must be 4 digits");
        }
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
        this.part4 = part4;
    }


    public String getCardNumber() {
        return String.join("-", part1, part2, part3, part4);
    }

    @Override
    public String toString() {
        return getCardNumber();
    }
}
