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

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}