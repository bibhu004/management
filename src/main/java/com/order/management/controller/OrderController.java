package com.order.management.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.management.DTO.OrderDTO;
import com.order.management.DTO.OrderResponseDTO;
import com.order.management.entity.Order;
import com.order.management.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        // try {
            OrderResponseDTO orderResponseDTO = orderService.placeOrder(orderDTO);

            return ResponseEntity.ok(orderResponseDTO);
        // } catch (Exception e) {
        //     return ResponseEntity.badRequest().body("unable to place order");
        // }
    }

    @GetMapping("/make/payment/{id}")
    public ResponseEntity<?> makePayment(@PathVariable Long id){
        try {
            Order order = orderService.makePayment(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment Failed");
        }
    }
}
