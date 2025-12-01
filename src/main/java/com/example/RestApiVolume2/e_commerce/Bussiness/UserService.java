

package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.UserRepository;
import com.example.RestApiVolume2.e_commerce.Entities.Order;
import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserCreateDto;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserDto;
import com.example.RestApiVolume2.e_commerce.RestApi.mapper.UserMapper;
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
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserMapper userMapper;

    @Autowired
    public UserService( UserRepository userRepository,@Lazy OrderService orderService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNorFoundException("User not found with id: " + id));
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
    public UserDto createUser(UserCreateDto dto) {
        User user = userMapper.toUser(dto);
        insert(user);
        return userMapper.toUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(long id) {
        User u = getById(id);
        return userMapper.toUserDto(u);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void cancelOrderOfUser(User user, Long orderId) {

        Order order = orderService.getById(orderId);
        if (user.getOrders().contains(order)) {
            user.getOrders().remove(order);
            update(user);
        } else {
            throw new ResourceNorFoundException("Order not found with id: " + orderId);
        }
    }
}
