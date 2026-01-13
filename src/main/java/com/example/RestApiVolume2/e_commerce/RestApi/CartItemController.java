/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.RestApi;

import com.example.RestApiVolume2.e_commerce.Bussiness.CartItemService;
import com.example.RestApiVolume2.e_commerce.Entities.CartItem;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 2005m
 */
@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{id}")
    public CartItem getCartItem(@PathVariable Long id) {

        return cartItemService.getById(id);

    }

    @GetMapping
    public List<CartItem> getAll() {
        return cartItemService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<CartItem> getCartItemsByUser(@PathVariable Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return cartItemService.getCartItemsByUserId(userId);
    }

    @PostMapping
    public CartItem addToCart(@RequestParam Long productId, @RequestParam Long userId, @RequestParam Integer quantity) {
        if (productId == null || userId == null || quantity == null) {
            throw new IllegalArgumentException("Product ID, User ID, and quantity cannot be null");
        }
        return cartItemService.addToCart(productId, userId, quantity);
    }

    @PutMapping("/{id}")
    public CartItem updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return cartItemService.updateQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
    }

    @DeleteMapping
    public void deleteFromCart(@RequestParam Long cartItemId, @RequestParam Long productId, @RequestParam Long userId) {

        cartItemService.deleteFromCart(cartItemId, productId, userId);
        System.out.println("warning");
    }

}
