package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/login")
public class AuthController {

    private final UserService service;

    private final RoleService roleService;

    public AuthController(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }


    @GetMapping
    public String login(@ModelAttribute("newUser") User user) {
        System.out.println("login " + user);
        return "login";
    }

    @PostMapping
    public String register(User user) {
        user.setRoles(List.of(roleService.getRoles().stream().filter(role -> role.getId() == 1).findFirst().get()));
        System.out.println("register " + user);
        service.saveUser(user);
        return "redirect:/login";
    }
}
