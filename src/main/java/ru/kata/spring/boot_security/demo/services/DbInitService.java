package ru.kata.spring.boot_security.demo.services;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDao;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Component
public class DbInitService implements ApplicationRunner {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder encoder;


    public DbInitService(UserDao userDao, RoleDao roleDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }


    @Transactional
    public void run(ApplicationArguments args) {
        roleDao.save(new Role(1,"ROLE_USER"));
        roleDao.save(new Role(2, "ROLE_ADMIN"));

        userDao.saveAndFlush(new User("John", "Johnson", 1988, "admin",
              encoder.encode("admin"),
              List.of(new Role(1,"ROLE_USER"), new Role(2, "ROLE_ADMIN"))));

        userDao.saveAndFlush(new User("Derek", "Stevenson", 1977, "user",
              encoder.encode("user"),
              List.of(new Role(1, "ROLE_USER"))));

    }
}