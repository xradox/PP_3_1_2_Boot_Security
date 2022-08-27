package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDao {

    @PersistenceContext
    private final EntityManager manager;

    public UserDao(EntityManager manager) {
        this.manager = manager;
    }

    public User findUserByUsername(String username) {
        TypedQuery<User> list = manager.createQuery("SELECT u from User u where u.username = :username", User.class)
                .setParameter("username", username);
        User user = list.getResultList().get(0);
        user.setRoles(getListAuthorities(user));
        return user;
    }

    public List<User> findAll() {
        return manager.createQuery("select u from User u", User.class).getResultList();
    }

    public User findById(int id) {
        return manager.find(User.class, id);
    }

    public void save(User user) {
        manager.persist(user);
    }

    public void saveAndFlush(User updated) {
        manager.merge(updated);
    }

    public void deleteById(int id) {
        manager.remove(findById(id));
    }

    public Set<Role> getListAuthorities (User user) {
        TypedQuery<Role> list = manager.createQuery("select r from Role r join r.users u where u.username = :username", Role.class)
                .setParameter("username", user.getUsername());
        return new HashSet<>(list.getResultList());
    }
}
