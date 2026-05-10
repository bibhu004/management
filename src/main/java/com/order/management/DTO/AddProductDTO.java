package com.order.management.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDTO {

    @NotBlank(message =  "Name cant be blank")
    private String name;

    @Positive(message = "Prive must be >=0")
    private double price;

    @Positive(message = "Prive must be >-+=0")
    private long units;

    

}
