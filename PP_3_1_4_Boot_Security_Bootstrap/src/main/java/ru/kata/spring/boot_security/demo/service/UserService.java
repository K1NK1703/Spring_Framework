package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User findUserByEmail(String email);

    void updateUser(Long id, String firstName, String lastName, byte age, String email, String password,
                    List<String> rolesList);

    void saveUser(User user, List<String> rolesList);

    void deleteUser(Long id);

    UserDetails loadUserByUsername(String email);
}
