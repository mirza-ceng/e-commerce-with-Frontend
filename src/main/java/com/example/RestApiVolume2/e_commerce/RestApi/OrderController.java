/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.RestApi;

import com.example.RestApiVolume2.e_commerce.Bussiness.OrderService;
import com.example.RestApiVolume2.e_commerce.Entities.Order;
import com.example.RestApiVolume2.e_commerce.Entities.Order.OrderStatus;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2005m
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestParam Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return orderService.createOrder(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(orderService.getById(id));

    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {

        return orderService.getById(id);
    }

    @PutMapping("/{id}")
    public Order cancelOrder(@PathVariable Long id) {

        return orderService.cancelOrder(id);
    }

    ;
    
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam OrderStatus newStatus
    ) {

      return  orderService.updateStatus(id, newStatus);

    }

}
