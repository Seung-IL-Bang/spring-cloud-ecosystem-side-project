package com.project.user_service.service;

import com.project.user_service.dto.UserDto;
import com.project.user_service.entity.Users;
import com.project.user_service.repository.UserRepository;
import com.project.user_service.util.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUIDGenerator.get());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        Users userEntity = Users.of(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public Iterable<Users> getAllUsers() {
        return null;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        return null;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        return null;
    }
}
