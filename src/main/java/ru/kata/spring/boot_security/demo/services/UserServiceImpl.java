package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(int id) {
        return  repository.findById(id).get();
    }

    @Transactional
    public void saveUser(User user) {
        repository.save(user);
    }

    @Transactional
    public void updateUser(User updated) {
        repository.saveAndFlush(updated);
    }

    @Transactional
    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
