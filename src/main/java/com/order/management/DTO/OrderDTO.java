package com.order.management.DTO;

import lombok.Data;

@Data
public class OrderDTO {


    private long userId;
    private long productId;
    private int quantity;
}   
