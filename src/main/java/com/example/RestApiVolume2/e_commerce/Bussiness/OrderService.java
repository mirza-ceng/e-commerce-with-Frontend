/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.OrderRepository;
import com.example.RestApiVolume2.e_commerce.Entities.CartItem;
import com.example.RestApiVolume2.e_commerce.Entities.Order;
import com.example.RestApiVolume2.e_commerce.Entities.Order.OrderStatus;
import com.example.RestApiVolume2.e_commerce.Entities.OrderItem;
import com.example.RestApiVolume2.e_commerce.Entities.Product;
import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.Exception.ResourceNorFoundException;
import com.example.RestApiVolume2.e_commerce.Exception.ValidationException;
import java.util.ArrayList;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final CartItemService cartItemService;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, @Lazy ProductService productService,
            @Lazy OrderItemService orderItemService, @Lazy UserService userService, @Lazy CartItemService cartItemService) {

        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Transactional
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(long id) {
        Optional<Order> item = orderRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ResourceNorFoundException("Order", "id", id);
        }

    }

    @Transactional

    public void delete(Order order) {
        getById(order.getId());
        orderRepository.delete(order);

    }

    private void update(Order order) {
        getById(order.getId());

        orderRepository.save(order);
    }

    @SuppressWarnings("null")
    private void insert(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public Order createOrder(Long userId) {

        User user = userService.getById(userId);
        List<CartItem> cartItems = user.getCartItems();
        List<OrderItem> items = new ArrayList<>();
        double totalPrice = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            OrderItem oi = orderItemService.cartItemToOrderItem(cartItems.get(i).getId());
            items.add(oi);
            totalPrice = totalPrice + (oi.getPrice() * oi.getQuantity());
        }
        // sepet itemleri sipariş itemlerine dönüştürüldü
        Order order = new Order();

        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDİNG);
        this.insert(order); // Order'ı önce kaydet, ID alsın

        for (OrderItem item : items) {
            item.setOrder(order); // Order referansını ayarla
            orderItemService.insert(item); // OrderItem'ı kaydet
        }

        // Order'ın orderItems listesini güncelle
        order.setOrderItems(items);

        // sepetten silme
        for (CartItem ci : user.getCartItems()) {
            cartItemService.delete(ci);
        }
        user.getCartItems().clear();
        user.getOrders().add(order);

        userService.update(user);
        return order;
        // userın orders ı ile senkronize et
    }

    @Transactional
    public Order updateStatus(Long orderId, OrderStatus status) {

        // if (userService.getById(userId).getRole() != User.UserRole.ADMİN) {
        // throw new ValidationException("Insufficient Role");
        // }
        Order order = getById(orderId);
        order.setStatus(status);
        update(order);
        return order;
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getById(orderId);
        OrderStatus status = order.getStatus();
        // İptal: yalnızca henüz gönderilmemiş/teslim edilmemiş ve iptal edilmemişse
        // yapılabilir
        if (status != OrderStatus.SHİPPED && status != OrderStatus.DELİVERED && status != OrderStatus.CANCELLED) {
            order.setStatus(OrderStatus.CANCELLED);
            for (OrderItem oi : order.getOrderItems()) {
                Product p = oi.getProduct();
                p.setStock(p.getStock() + oi.getQuantity());
                productService.update(p);
            }
            userService.cancelOrderOfUser(order.getUser(), orderId);
            update(order);
            return order;
        } else {
            throw new ValidationException("INVALID STATUS");
        }

    }

}
