package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Transactional(readOnly = true)
    public Role findRole(String name) {
        return roleDao.findByName(name);
    }
}
