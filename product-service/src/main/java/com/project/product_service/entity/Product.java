package com.project.product_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productId;

    private String productName;

    private Integer stock;

    private Integer unitPrice;

    private LocalDateTime createdAt;
}
