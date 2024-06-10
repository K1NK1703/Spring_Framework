package ru.spring_mvc_hibernate.service;

import org.springframework.stereotype.Service;
import ru.spring_mvc_hibernate.model.User;
import ru.spring_mvc_hibernate.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User deleteUser(long id) {
        User user = null;
        try {
            user = userRepository.deleteUser(id);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        if (0 == user.getId()) {
            userRepository.createUser(user);
        } else {
            userRepository.updateUser(user);
        }
    }
}
