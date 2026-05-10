
package com.order.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.management.DTO.AddProductDTO;
import com.order.management.DTO.ProductResponseDTO;
import com.order.management.service.AdminService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/add/product")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductDTO addProductDTO){

        // try {
            ProductResponseDTO  productResponseDTO = adminService.addProduct(addProductDTO);

            return ResponseEntity.ok(productResponseDTO);
        // } catch (Exception e) {
        //     // TODO: handle exception
        //     return ResponseEntity.badRequest().body(e.getMessage());
        // }
    }
}
