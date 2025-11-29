package com.example.RestApiVolume2.e_commerce.RestApi.mapper;

import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserCreateDto;
import com.example.RestApiVolume2.e_commerce.RestApi.dto.UserDto;

public class UserMapper {

    public static User toEntity(UserCreateDto d) {
        User u = new User();
        u.setUserName(d.getUserName());
        u.setEmail(d.getEmail());
        u.setPassword(d.getPassword());
        return u;
    }

    public static UserDto toDto(User u) {
        UserDto d = new UserDto();
        d.setUserId(u.getUserId());
        d.setUserName(u.getUserName());
        d.setEmail(u.getEmail());
        return d;
    }
}
