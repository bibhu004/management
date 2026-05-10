package com.order.management.DTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.order.management.entity.Product;
import com.order.management.entity.Unit;
import com.order.management.entity.User;

import lombok.Data;

@Data
public class OrderResponseDTO {

    private long id;
    private LocalDateTime datetime;
    private User user;
    private double amount;

    private String paymentStatus;
    HashMap<Product, List<Unit>> productUnitMapping;
    
}
