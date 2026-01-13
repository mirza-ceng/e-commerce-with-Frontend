/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author 2005m
 */
@Entity
@Table(name="orderItem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderItem {
    
    @Id
    @Column(name="orderItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name ="quantity",nullable =false)
    private int quantity;
    
    @Column(name="price",nullable = false)
    private double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id",nullable =false)
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable =false)
    @JsonIgnore
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
}
