package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User getUser(String name);

    User saveUser(User user);

    User updateUser(User updated);

    void deleteUser(User user);
}
