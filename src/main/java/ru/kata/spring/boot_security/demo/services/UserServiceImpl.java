package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userdao;


    private final PasswordEncoder encoder;

    public UserServiceImpl(UserDao userdao, PasswordEncoder encoder) {
        this.userdao = userdao;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userdao.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(int id) {
        User user = userdao.findById(id);
        user.setRoles(getAuthorities(user));
        return user;
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userdao.saveAndFlush(user);
    }

    @Transactional
    public void updateUser(User updated) {
        if(updated.getPassword().isEmpty()) {
            updated.setPassword(getUser(updated.getId()).getPassword());
        } else {
            updated.setPassword(encoder.encode(updated.getPassword()));
        }
        userdao.saveAndFlush(updated);
    }

    @Transactional
    public void deleteUser(int id) {
        userdao.deleteById(id);
    }

    @Transactional
    public List<Role> getAuthorities (User user) {
        return userdao.getListAuthorities(user);
    }
}
