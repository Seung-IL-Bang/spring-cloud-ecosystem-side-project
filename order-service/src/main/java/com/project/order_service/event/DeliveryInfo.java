package com.project.order_service.event;

import lombok.Data;

@Data
public class DeliveryInfo {

    private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;

}
