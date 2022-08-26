package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collections;

@Service
public class RegistrationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public RegistrationService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public void registerNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        repository.save(user);
    }
}
