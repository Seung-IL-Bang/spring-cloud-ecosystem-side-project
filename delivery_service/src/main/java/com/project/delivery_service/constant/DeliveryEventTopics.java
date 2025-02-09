package com.project.delivery_service.constant;

public enum DeliveryEventTopics {

    DELIVERY_RECEIVED("배달 접수"),
    DELIVERY_PREPARING("배달 준비중"),
    DELIVERY_STARTED("배달 중"),
    DELIVERY_DELIVERED("배달 완료"),
    DELIVERY_CANCELLED("배달 취소");

    private String description;

    DeliveryEventTopics(String description) {
        this.description = description;
    }
}
