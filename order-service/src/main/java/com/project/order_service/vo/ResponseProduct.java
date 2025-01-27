package com.project.order_service.vo;

import lombok.Data;

@Data
public class ResponseProduct {

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

}
