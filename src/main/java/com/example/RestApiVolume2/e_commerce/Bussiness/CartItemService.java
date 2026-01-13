/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.CartItemRepository;

import com.example.RestApiVolume2.e_commerce.Entities.CartItem;
import com.example.RestApiVolume2.e_commerce.Entities.Product;
import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.Exception.ResourceNorFoundException;
import com.example.RestApiVolume2.e_commerce.Exception.ValidationException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 2005m
 */
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, @Lazy UserService userService,
            ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    @Transactional

    public CartItem getById(long id) {
        Optional<CartItem> item = cartItemRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ResourceNorFoundException("CartItem", "id", id);
        }

    }

    @SuppressWarnings("null")
    @Transactional

    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);

    }

    @Transactional

    public void update(CartItem cartItem) {
        getById(cartItem.getId());

        cartItemRepository.save(cartItem);
    }

    @SuppressWarnings("null")
    @Transactional

    public void insert(CartItem cartItem) {

        cartItemRepository.save(cartItem);
    }

    @Transactional
    public CartItem addToCart(long productId, long userId, int quantity) {
       
        // Fetch the User and the Product first
        User user = userService.getById(userId);
        Product product = productService.getById(productId);

        // Check if the item is already in the user's cart
        Optional<CartItem> existingItemOptional = user.getCartItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        if (existingItemOptional.isPresent()) {
            // Item exists, update quantity
            CartItem existingItem = existingItemOptional.get();
            // First check stock
            if (!productService.stockControl(productId, quantity)) {
                throw new ValidationException("Stock isn't enough!!");
            }
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            // The transaction will automatically save the updated item
            return existingItem;
        } else {
            // Item does not exist, check stock and add it
            if (!productService.stockControl(productId, quantity)) {
                throw new ValidationException("Stock isn't enough!!");
            }

            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setUser(user); // Set the owning side
            newCartItem.setQuantity(quantity);

            user.getCartItems().add(newCartItem); // Add to the user's list

            // Because of CascadeType.ALL on User.cartItems, we don't need to save the cartItem explicitly.
            // The changes to the User entity will be detected and persisted at the end of the transaction.
            return newCartItem;
        }

    }

    @Transactional
    public void deleteFromCart(long cartItemId, long productId, long userId) {
        Optional<CartItem> item = cartItemRepository.findByUserUserIdAndProductId(userId, productId);

        if (item.isPresent()) {
            this.delete(item.get());
            var user = userService.getById(userId);
            if (user != null) {

                userService.getById(userId).getCartItems().remove(item.get());

            }

        } else {
            throw new ResourceNorFoundException("CartItem", "id", cartItemId);
        }

    }
    
    @Transactional
    public List<CartItem> getCartItemsByUserId(long userId) {
        return cartItemRepository.findByUserUserId(userId);
    }
}
