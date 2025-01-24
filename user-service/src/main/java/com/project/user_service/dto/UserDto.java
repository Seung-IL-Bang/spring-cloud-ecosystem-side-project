package com.project.user_service.dto;

import com.project.user_service.vo.ResponseOrder;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String email;
    private String userId;
    private String password;
    private String name;
    private String createdAt;
    private String encryptedPassword;
    private List<ResponseOrder> orders;

}
