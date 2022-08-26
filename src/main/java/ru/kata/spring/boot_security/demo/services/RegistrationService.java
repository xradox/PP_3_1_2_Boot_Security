package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleRepository;
import ru.kata.spring.boot_security.demo.DAO.UserRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    private void addRoles() {
        roleRepository.save(new Role(1,"ROLE_USER"));
        roleRepository.save(new Role(2, "ROLE_ADMIN"));
    }

    @Transactional
    public void registerNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        userRepository.save(user);
    }
}
