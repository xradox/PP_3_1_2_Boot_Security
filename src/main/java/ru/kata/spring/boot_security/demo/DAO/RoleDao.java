package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDao {

    @PersistenceContext
    private final EntityManager manager;

    public RoleDao(EntityManager manager) {
        this.manager = manager;
    }

    public void save(Role role) {
        manager.merge(role);
    }
}
