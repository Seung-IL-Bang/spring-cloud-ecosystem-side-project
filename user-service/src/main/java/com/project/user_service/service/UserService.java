package com.project.user_service.service;

import com.project.user_service.dto.UserDto;
import com.project.user_service.entity.Users;

public interface UserService {

    UserDto getUserByUserId(String userId);
    Iterable<Users> getAllUsers();
    UserDto createUser(UserDto userDto);
    UserDto getUserDetailsByEmail(String email);
}
