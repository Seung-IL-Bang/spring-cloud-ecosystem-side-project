package com.project.order_service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct {

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

}
