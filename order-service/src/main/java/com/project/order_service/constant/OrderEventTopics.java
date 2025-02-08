package com.project.order_service.constant;

public enum OrderEventTopics {

    ORDER_CREATED("주문 생성"),
    ORDER_COMPLETED("주문 완료"),
    ORDER_CANCELLED("주문 취소");

    private String event;

    OrderEventTopics(String event) {
        this.event = event;
    }
}
