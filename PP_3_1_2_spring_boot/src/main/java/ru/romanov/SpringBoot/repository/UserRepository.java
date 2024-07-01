package ru.romanov.SpringBoot.repository;


import ru.romanov.SpringBoot.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    User deleteUser(long id);
}
