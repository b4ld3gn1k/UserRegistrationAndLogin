package com.example.UserRegister.controllers;


import com.example.UserRegister.entity.User;
import com.example.UserRegister.entity.UserValidationData;
import com.example.UserRegister.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {
    private UserService userService;

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

    @PostMapping("/reg/save")
    public String registration(@Valid @ModelAttribute("user") UserValidationData userValidationData, BindingResult result, Model model) {
        User existing = userService.findByUsername(userValidationData.getUsername());

        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userValidationData);
            return "reg";
        }
        userService.saveUser(userValidationData);
        return "redirect:/reg?success";
    }

}
