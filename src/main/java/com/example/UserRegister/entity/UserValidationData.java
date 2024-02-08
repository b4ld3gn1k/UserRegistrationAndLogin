package com.example.UserRegister.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserValidationData {

    private Long id;

    @NotEmpty(message = "Email is empty!")
    @Email
    private String email;

    @NotEmpty(message = "Username is empty!")
    private String username;

    @NotEmpty(message = "Password is empty!")
    private String password;
}
