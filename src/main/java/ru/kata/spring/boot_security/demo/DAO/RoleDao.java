package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.Set;

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

    public Set<Role> getRoles() {
        TypedQuery<Role> query = manager.createQuery("select r from Role r", Role.class);
        return new HashSet<>(query.getResultList());
    }
}
