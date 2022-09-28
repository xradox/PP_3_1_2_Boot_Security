package ru.kata.spring.boot_security.demo.services;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Component
public class DbInitService implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;


    public DbInitService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public void run(ApplicationArguments args) {
        roleService.save(new Role("ROLE_ADMIN", "ADMIN"));
        roleService.save(new Role("ROLE_USER", "USER"));

        userService.saveUser(new User("John", "Johnson", 33, "admin",
                "admin", Set.of(roleService.findRole("ROLE_ADMIN"), roleService.findRole("ROLE_USER"))));
        userService.saveUser(new User("Derek", "Stevenson", 44, "user",
                "user", Set.of(roleService.findRole("ROLE_USER"))));
    }
}