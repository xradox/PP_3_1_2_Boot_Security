package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
        return list.getResultList().get(0);
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
}
