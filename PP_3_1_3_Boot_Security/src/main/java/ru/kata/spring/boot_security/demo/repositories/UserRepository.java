package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository {
    void createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User findUserByEmail(String email);

    void updateUser(User user);

    void deleteUser(Long id);
}
