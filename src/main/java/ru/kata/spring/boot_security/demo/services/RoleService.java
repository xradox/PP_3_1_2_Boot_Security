package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public Set<Role> getRoles() {
        return roleDao.getRoles();
    }
}
