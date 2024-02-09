package com.example.UserRegister.service;

import com.example.UserRegister.entity.Role;
import com.example.UserRegister.entity.User;
import com.example.UserRegister.entity.UserValidationData;
import com.example.UserRegister.repositories.RoleRepository;
import com.example.UserRegister.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserValidationData userValidationData) {
        User user = new User();
        user.setEmail(userValidationData.getEmail());
        user.setUsername(userValidationData.getUsername());
        user.setPhone(userValidationData.getPhone());
        user.setPassword(passwordEncoder.encode(userValidationData.getPassword()));
        Role role = roleRepository.findByName("ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserValidationData> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserValidationData convertEntityToDto(User user) {
        UserValidationData userValidationData = new UserValidationData();
        userValidationData.setEmail(user.getEmail());
        userValidationData.setUsername(user.getUsername());
        userValidationData.setPhone(user.getPhone());
        return userValidationData;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ADMIN");
        return roleRepository.save(role);
    }
}
