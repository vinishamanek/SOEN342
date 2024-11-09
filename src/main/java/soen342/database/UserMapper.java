package soen342.database;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

import soen342.users.User;

public class UserMapper extends AbstractMapper<User> {
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByCredentials(String email, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}