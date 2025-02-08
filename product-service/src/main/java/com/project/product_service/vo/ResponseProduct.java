package com.project.product_service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProduct {

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

}
