package ru.spring_mvc_hibernate.service;

import ru.spring_mvc_hibernate.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    User deleteUser(long id);
}
