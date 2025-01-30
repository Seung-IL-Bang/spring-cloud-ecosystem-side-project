package com.project.user_service.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email must not be less than 2 characters")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password must not be less than 4 characters")
    private String password;

}
