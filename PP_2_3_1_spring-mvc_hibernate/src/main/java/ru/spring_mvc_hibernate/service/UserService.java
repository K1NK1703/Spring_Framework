package ru.spring_mvc_hibernate.service;

import ru.spring_mvc_hibernate.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(long id);

    User deleteUser(long id);

    void saveUser(User user);
}
