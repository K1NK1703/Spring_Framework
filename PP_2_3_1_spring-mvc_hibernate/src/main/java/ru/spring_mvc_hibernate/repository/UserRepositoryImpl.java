package ru.spring_mvc_hibernate.repository;

import org.springframework.stereotype.Repository;
import ru.spring_mvc_hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User deleteUser(long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new NullPointerException("User not found");
        }
        entityManager.remove(user);
        entityManager.flush();
        return user;
    }
}
