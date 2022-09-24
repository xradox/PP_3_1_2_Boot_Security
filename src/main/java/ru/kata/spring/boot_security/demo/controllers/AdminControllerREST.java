package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminControllerREST {

    private final UserService userService;
    private final RoleService roleService;

    public AdminControllerREST(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/roles")
    public List<Role> getAllRoles() {
        return roleService.getRoles();
    }

    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/users/{id}")
    public User getUserById(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("/admin/users")
    public User createUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.getUser(user.getUsername());
    }

    @PatchMapping("/admin/users/{id}")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/admin/users/{id}")
    public void deleteUser(@PathVariable("id") User user) {
        userService.deleteUser(userService.getUser(user.getId()));
    }

    @GetMapping("/user")
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
