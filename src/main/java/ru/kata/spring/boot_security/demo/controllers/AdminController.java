package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    public AdminController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable int id, Model model) {
        model.addAttribute("user", service.getUser(id));
        return "admin/userpage";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("newUser") User newUser) {
        return "admin/new";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", service.getUser(id));
        return "admin/edit";
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
    public String editUser(@ModelAttribute User updated) {
        service.updateUser(updated);
        return "redirect:/admin";
    }
}
