/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.CartItemRepository;

import com.example.RestApiVolume2.e_commerce.Entities.CartItem;
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

    private CartItemRepository cartItemRepository;
    private UserService userService;
    private ProductService productService;

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

    @Transactional

    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);

    }

    @Transactional

    public void update(CartItem cartItem) {
        getById(cartItem.getId());

        cartItemRepository.save(cartItem);
    }

    @Transactional

    public void insert(CartItem cartItem) {

        cartItemRepository.save(cartItem);
    }

    @Transactional

    public CartItem addToCart(long productId, long userId, int quantity) {
        Optional<CartItem> item = cartItemRepository.findByUserUserIdAndProductId(userId, productId);

        if (item.isPresent()) {
            // sepette var
            CartItem existing = item.get();
            existing.setQuantity(existing.getQuantity() + quantity);
            update(existing);
            // kullanıcı nesnesini alıp eşleşen cartItem'ın quantity'sini güncelle
            var user = userService.getById(userId);
            if (user != null) {
                user.getCartItems().stream()
                        .filter(ci -> ci.getId() == existing.getId())
                        .findFirst()
                        .ifPresent(ci -> ci.setQuantity(existing.getQuantity()));
            }
            return existing;
        } else {
            // sepette yok

            if (productService.stockControl(productId, quantity)) {

                CartItem cartItem = new CartItem();
                cartItem.setProduct(productService.getById(productId));
                cartItem.setUser(userService.getById(userId));
                cartItem.setQuantity(quantity);

                insert(cartItem);
                var user = userService.getById(userId);
                if (user != null) {

                    user.addCartItem(cartItem);
                }
                return cartItem;

            }
            throw new ValidationException("Stock isn't enough!!");

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
}
