package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RegistrationService;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService service;

    public AuthController(RegistrationService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "auth/register";
    }

    @PostMapping("/registration")
    public String registerNewUser(User user) {
        service.registerNewUser(user);
        return "redirect:/auth/login";
    }
}
