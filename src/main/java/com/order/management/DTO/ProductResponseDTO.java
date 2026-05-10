package com.order.management.DTO;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long productId;

    private String name;

    private double price;

    private long totalUnits;

    private String message;
}