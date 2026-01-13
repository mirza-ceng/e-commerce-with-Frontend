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
@Table(name = "CartItem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CartItem {

    @Id
    @Column(name = "cartItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "quantity")
    private int quantity;

   @ManyToOne(fetch = FetchType.LAZY)
   @JsonIgnore
    private User user;
   @ManyToOne(fetch = FetchType.LAZY)
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
