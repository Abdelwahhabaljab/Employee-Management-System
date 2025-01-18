package com.example.employee_management_system.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data

public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;




}

