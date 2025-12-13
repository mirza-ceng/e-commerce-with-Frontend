/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.OrderItemRepository;

import com.example.RestApiVolume2.e_commerce.Entities.OrderItem;
import com.example.RestApiVolume2.e_commerce.Exception.ResourceNorFoundException;
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
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository,@Lazy CartItemService cartItemService) {
        this.orderItemRepository = orderItemRepository;
        this.cartItemService = cartItemService;
    }

    @Transactional
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @Transactional

    public OrderItem getById(long id) {

        Optional<OrderItem> item = orderItemRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ResourceNorFoundException("OrderItem", "id", id);
        }

    }

    @Transactional

    public void delete(OrderItem orderItem) {
        getById(orderItem.getId());
        orderItemRepository.delete(orderItem);

    }

    @SuppressWarnings("null")
    @Transactional

    public void delete(Long id) {
        OrderItem orderItem = getById(id);

        orderItemRepository.delete(orderItem);

    }

    @Transactional

    public void update(OrderItem orderItem) {
        getById(orderItem.getId());
        orderItemRepository.save(orderItem);
    }

    @SuppressWarnings("null")
    @Transactional

    public void insert(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Transactional
    public OrderItem cartItemToOrderItem(Long cartitemId) {
        var cartItem = cartItemService.getById(cartitemId);
        OrderItem item = new OrderItem();
        item.setPrice(cartItem.getProduct().getPrice());
        item.setProduct(cartItem.getProduct());
        item.setQuantity(cartItem.getQuantity());

        return item;

    }

}
