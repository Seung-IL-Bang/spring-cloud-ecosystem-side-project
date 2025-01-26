package com.project.user_service.entity;

import com.project.user_service.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String userId;

    private String name;

    private String encryptedPassword;

    public static Users of(UserDto userDto) {
        return Users.builder()
                .email(userDto.getEmail())
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .encryptedPassword(userDto.getEncryptedPassword())
                .build();
    }
}
