package com.project.user_service.service;

import com.project.user_service.dto.UserDto;
import com.project.user_service.entity.Users;

import java.util.List;

public interface UserService {

    UserDto getUserByUserId(String userId);
    List<Users> getAllUsers();
    UserDto createUser(UserDto userDto);
    UserDto getUserDetailsByEmail(String email);
}
