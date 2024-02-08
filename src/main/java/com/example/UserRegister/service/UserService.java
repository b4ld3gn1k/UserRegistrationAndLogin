package com.example.UserRegister.service;

import com.example.UserRegister.entity.User;
import com.example.UserRegister.entity.UserValidationData;

import java.util.List;

public interface UserService {
    void saveUser(UserValidationData userValidationData);

    User findByUsername(String username);

    List<UserValidationData> findAllUsers();
}
