package soen342.database;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

import soen342.catalogs.UserCatalog;
import soen342.users.User;

public class UserMapper extends AbstractMapper<User> {

    private UserCatalog userCatalog;

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
        // @todo popupate userCatalog using findALl() method
        // @then search through the catalog to find the user with the email (implement
        // findByEmail in catalog? or not even bother with that?)
        // @then check if the password matches
        // @note this is awfully inefficient
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

    public List<User> findAllNonAdmins() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE TYPE(u) != Admin", User.class);
        return query.getResultList();
    }
}