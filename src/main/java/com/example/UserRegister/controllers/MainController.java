package com.example.UserRegister.controllers;


import com.example.UserRegister.entity.User;
import com.example.UserRegister.entity.UserValidationData;
import com.example.UserRegister.repositories.UserRepository;
import com.example.UserRegister.service.UserService;
import com.example.UserRegister.token.JwtCore;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String rootPage() {
        return "startPage";
    }

    @GetMapping("/startPage")
    public String startPage() {
        return "startPage";
    }


    @GetMapping("/main")
    public String listRegisteredUsers(Model model) {
        List<UserValidationData> users = userService.findAllUsers();
        model.addAttribute("user", users);
        return "main";
    }

    @GetMapping("/login_reg")
    public String loginPage() {
        return "login_reg";
    }

    @GetMapping("/reg")
    public String regPage(Model model) {
        UserValidationData user = new UserValidationData();
        model.addAttribute("user", user);
        return "reg";
    }

    @PostMapping("/reg")
    public String signup(@Valid @ModelAttribute("user") UserValidationData userValidationData, BindingResult result, Model model) {
        User username = userService.findByUsername(userValidationData.getUsername());

        if (username != null) {
            result.rejectValue("username", "wrong", "There is already an account registered with that username");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userValidationData);
            return "reg";
        }
        userService.saveUser(userValidationData);
        return "redirect:/login_reg";
    }

}
