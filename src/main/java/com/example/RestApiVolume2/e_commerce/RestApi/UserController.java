package com.example.RestApiVolume2.e_commerce.RestApi;

import com.example.RestApiVolume2.e_commerce.Bussiness.UserService;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserCreateDto;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final  UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        return userService.getUserDtoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserCreateDto body, HttpServletResponse response) {
        UserDto created = userService.createUser(body);
        response.setHeader("Location", "/api/users/" + created.getUserId());
        return created;
    }

}
