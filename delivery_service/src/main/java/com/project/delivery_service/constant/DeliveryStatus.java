package com.project.delivery_service.constant;


public enum DeliveryStatus {

    RECEIVED("배달 접수"),
    PREPARING("배송 준비중"),
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("배송취소");


    private String description;

    DeliveryStatus(String description) {
        this.description = description;
    }
}
