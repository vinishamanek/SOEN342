package soen342.database;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

import soen342.catalogs.UserCatalog;
import soen342.users.User;

public class UserMapper extends AbstractMapper<User> {

    private UserCatalog userCatalog;

    public UserMapper() {
        super();
        userCatalog = UserCatalog.getInstance();
    }

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
        List<User> users = findAll();
        userCatalog.setUsers(users);
        return userCatalog.findByCredentials(email, password);
    }

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public List<User> findAllNonAdmins() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE TYPE(u) != Admin", User.class);
        return query.getResultList();
    }
}