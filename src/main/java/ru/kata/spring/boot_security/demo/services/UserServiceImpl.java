package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;


    private final PasswordEncoder encoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getUser(String name) {
        return userDao.findUserByUsername(name);
    }

    @Transactional
    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.saveAndFlush(user);
        return user;
    }

    @Transactional
    public User updateUser(User updated) {
        if(!updated.getPassword().equals(userDao.findUserByUsername(updated.getUsername()).getPassword())) {
            updated.setPassword(encoder.encode(updated.getPassword()));
        }
        userDao.saveAndFlush(updated);
        return updated;
    }

    @Transactional
    public void deleteUser(User user) {
        userDao.deleteById(user.getId());
    }
}
