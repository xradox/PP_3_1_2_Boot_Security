package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<Role> getRoles() {
        TypedQuery<Role> query = manager.createQuery("select r from Role r", Role.class);
        return query.getResultList();
    }

    public Role findByName(String roleName) {
        return manager.createQuery("SELECT r from Role r where r.name = :name", Role.class)
                .setParameter("name", roleName).getResultList().get(0);
    }
}
