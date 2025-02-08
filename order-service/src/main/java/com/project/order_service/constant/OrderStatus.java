package com.project.order_service.constant;

public enum OrderStatus {

    ORDERED("주문 접수"),
    CANCELLED("주문 취소"),
    COMPLETED("주문 완료");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
