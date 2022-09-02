package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    private final RoleService roleService;

    private final UserDetailsServiceImpl detailsService;

    public AdminController(UserServiceImpl service, RoleService roleService, UserDetailsServiceImpl detailsService) {
        this.service = service;
        this.roleService = roleService;
        this.detailsService = detailsService;
    }

    @GetMapping
    public String getUsers(@ModelAttribute("newUser") User user,
                           Model model) {
        service.getAllUsers().forEach(user1 -> System.out.println(user1.getPassword()));
        model.addAttribute("users", service.getAllUsers());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("currentUser",
                detailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "admin/index";
    }

    @PostMapping
    public String saveUser(User user) {
        service.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("newUser") User updated) {
        service.updateUser(updated);
        return "redirect:/admin";
    }
}
