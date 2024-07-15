package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> getAllRoles();

    List<Role> findRolesById(List<Long> id);

    Role findRoleByName(String name);
}
