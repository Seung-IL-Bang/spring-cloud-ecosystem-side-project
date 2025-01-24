package com.project.user_service.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {

    @NotNull
    @Size(min = 2, message = "Email must be at least 2 characters long")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;
}
