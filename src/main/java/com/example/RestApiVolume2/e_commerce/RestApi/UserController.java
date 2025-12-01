package com.example.RestApiVolume2.e_commerce.RestApi;

import com.example.RestApiVolume2.e_commerce.Bussiness.UserService;
import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.Exception.ResourceNorFoundException;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserCreateDto;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserDto;
import com.example.RestApiVolume2.e_commerce.RestApi.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        return userService.getUserDtoById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserCreateDto body) {
        return userService.createUser(body);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserCreateDto body) {
        User user = userService.findByEmail(body.getEmail())
                .orElseThrow(() -> new ResourceNorFoundException("User not found with email: " + body.getEmail()));

        if (user.getPassword().equals(body.getPassword())) {
            return userMapper.toUserDto(user);
        } else {
            throw new ResourceNorFoundException("Invalid password");
        }
    }
}
