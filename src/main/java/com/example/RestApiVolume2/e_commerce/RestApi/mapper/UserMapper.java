package com.example.RestApiVolume2.e_commerce.RestApi.mapper;

import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserCreateDto;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserCreateDto d) {
        User u = new User();
        u.setUserName(d.getName());
        u.setEmail(d.getEmail());
        u.setPassword(d.getPassword());
        return u;
    }

    public UserDto toUserDto(User u) {
        UserDto d = new UserDto();
        d.setUserId(u.getUserId());
        d.setName(u.getUserName());
        d.setEmail(u.getEmail());
        return d;
    }
}
