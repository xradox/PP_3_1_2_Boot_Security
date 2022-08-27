package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userdao;

    public UserServiceImpl(UserDao userdao) {
        this.userdao = userdao;
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
        userdao.save(user);
    }

    @Transactional
    public void updateUser(User updated) {
        userdao.saveAndFlush(updated);
    }

    @Transactional
    public void deleteUser(int id) {
        userdao.deleteById(id);
    }

    @Transactional
    public Set<Role> getAuthorities (User user) {
        return userdao.getListAuthorities(user);
    }
}
