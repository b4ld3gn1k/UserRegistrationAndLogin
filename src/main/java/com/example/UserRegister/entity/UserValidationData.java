package com.example.UserRegister.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserValidationData {

    private Long id;

    @NotEmpty(message = "Email is empty!")
    @Email(regexp = "^[a-zA-Z0-9].[a-zA-Z0-9\\._%\\+\\-]{0,63}@[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,30}$")
    private String email;

    @NotEmpty(message = "Phone is empty!")
    @Pattern(regexp = "^(?<code>\\+?\\d{1,3})[-\\s]{0,}(?<number>\\(?\\d{3}\\)?[-\\s]{0,}\\d{3}[-\\s]{0,}\\d{2}[-\\s]{0,}\\d{2})$")
    private String phone;

    @NotEmpty(message = "Username is empty!")
    private String username;

    @NotEmpty(message = "Password is empty!")
    private String password;
}
