/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.RestApi;

import com.example.RestApiVolume2.e_commerce.Bussiness.OrderItemService;
import com.example.RestApiVolume2.e_commerce.Entities.OrderItem;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2005m
 */
@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItem(@PathVariable Long id) {
        return orderItemService.getById(id);
    }

    @GetMapping
    public List<OrderItem> getAll() {

        return orderItemService.getAll();
    }

   @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
orderItemService.delete(id);
    }
    
   
    
    
    

}
