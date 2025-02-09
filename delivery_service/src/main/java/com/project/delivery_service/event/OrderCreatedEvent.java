package com.project.delivery_service.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.delivery_service.entity.AddressInfo;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreatedEvent {

    private String orderId;
    private DeliveryInfo deliveryInfo;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DeliveryInfo {
        private String recipientName;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private String phoneNumber;

        public AddressInfo toAddressInfo() {
            return new AddressInfo(addressLine1, addressLine2, city, state, postalCode, country);
        }
    }
}
