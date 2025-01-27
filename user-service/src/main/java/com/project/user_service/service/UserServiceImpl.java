package com.project.user_service.service;

import com.project.user_service.dto.UserDto;
import com.project.user_service.entity.Users;
import com.project.user_service.feign.OrderApiService;
import com.project.user_service.repository.UserRepository;
import com.project.user_service.util.UUIDGenerator;
import com.project.user_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final OrderApiService orderApiService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUIDGenerator.get());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        Users userEntity = Users.of(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        Optional<Users> findUser = userRepository.findByUserId(userId);

        if (findUser.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        UserDto userDto = modelMapper.map(findUser.get(), UserDto.class);

        ResponseEntity<List<ResponseOrder>> orders = orderApiService.getOrdersByUserId(userId);
        if (orders.getBody() == null) {
            log.info("Order not found by User ID : {}", userId);
            userDto.setOrders(Collections.emptyList());
        } else {
            userDto.setOrders(orders.getBody());
        }

        return userDto;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        Users users = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return modelMapper.map(users, UserDto.class);
    }
}
