package ru.spring_mvc_hibernate.repository;

import ru.spring_mvc_hibernate.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    User deleteUser(long id);
}
