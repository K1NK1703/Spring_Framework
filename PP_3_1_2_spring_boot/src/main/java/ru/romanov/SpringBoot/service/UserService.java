package ru.romanov.SpringBoot.service;

import ru.romanov.SpringBoot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    User deleteUser(long id);
}
