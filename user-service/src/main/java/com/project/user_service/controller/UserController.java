package com.project.user_service.controller;

import com.project.user_service.dto.UserDto;
import com.project.user_service.entity.Users;
import com.project.user_service.service.UserService;
import com.project.user_service.vo.RequestUser;
import com.project.user_service.vo.ResponseUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestUser requestUser) {
        UserDto userDto = modelMapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);
        return ResponseEntity.ok(modelMapper.map(userDto, ResponseUser.class));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        List<ResponseUser> result = users.stream()
                .map(user -> modelMapper.map(user, ResponseUser.class))
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUserByUserId(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser responseUser = modelMapper.map(userDto, ResponseUser.class);
        return ResponseEntity.ok(responseUser);
    }
}
