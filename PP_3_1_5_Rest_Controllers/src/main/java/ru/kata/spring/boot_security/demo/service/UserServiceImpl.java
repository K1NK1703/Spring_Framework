package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        User newUser = userRepository.getUserById(user.getId());

        if (newUser != null) {
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setAge(user.getAge());
            newUser.setEmail(user.getEmail());
            newUser.setRoles(user.getRoles());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            saveUser(newUser);
        }
    }

    @Override
    public void saveUser(User user) {
        List<Role> roles = roleRepository.findRolesById(user.getRoles().stream().map(Role::getName).collect(Collectors.toList())
                                                                        .stream().map(Long::valueOf).collect(Collectors.toList()));

        boolean hasAdminRole = roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        if (hasAdminRole) {
            Role userRole = roleRepository.findRoleByName("ROLE_USER");
            if (userRole != null && !roles.contains(userRole)) {
                roles.add(userRole);
            }
        }

        user.setRoles(roles);
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.createUser(user);
        } else {
            userRepository.updateUser(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found!");
        }

        return user;
    }
}
