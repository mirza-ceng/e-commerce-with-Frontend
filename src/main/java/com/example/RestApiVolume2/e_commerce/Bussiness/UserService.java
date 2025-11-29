/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.UserRepository;
import com.example.RestApiVolume2.e_commerce.Entities.Order;
import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserCreateDto;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserDto;
import com.example.RestApiVolume2.e_commerce.RestApi.mapper.UserMapper;
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
public class UserService {

    private UserRepository userRepository;
    private OrderService orderService;

    @Autowired
    public UserService( UserRepository userRepository,@Lazy OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getById(long id) {
        Optional<User> item = userRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ResourceNorFoundException("User", "id", id);
        }
    }

    @Transactional
    public void delete(User user) {
        getById(user.getUserId());

        userRepository.delete(user);
    }

    @Transactional
    public void update(User user) {
        getById(user.getUserId());
        userRepository.save(user);
    }

    private void insert(User user) {

        userRepository.save(user);
    }

    @Transactional
    public User createUser(String userName, String email, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        insert(user);
        return user;
    }

    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        User user = UserMapper.toEntity(dto);
        insert(user);
        return UserMapper.toDto(user);
    }

    @Transactional
    public UserDto getUserDtoById(long id) {
        User u = getById(id);
        return UserMapper.toDto(u);
    }

    @Transactional
    public User logIn(String userNameOrEmail, String password) {

        User loggedUser = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail);

        if (loggedUser == null) {
            throw new ValidationException("COULDN'T FİND USER!!");
        }

        if (loggedUser.getPassword().equals(password)) {
            return loggedUser;
        } else {
            throw new ValidationException("COULDN'T FİND USER!!");
        }

    }

    @Transactional
    public void cancelOrderOfUser(User user, Long orderId) {
       
        Order order = orderService.getById(orderId);
        if (user.getOrders().contains(order)) {
            user.getOrders().remove(order);
            update(user);
        } else {
            throw new ResourceNorFoundException("Order", "Order", order);
        }

    }

}
