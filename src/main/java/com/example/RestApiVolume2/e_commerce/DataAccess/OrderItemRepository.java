/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.DataAccess;

import com.example.RestApiVolume2.e_commerce.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 2005m
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    
}
