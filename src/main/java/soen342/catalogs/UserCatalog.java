package soen342.catalogs;

import java.util.List;
import java.util.ArrayList;
import soen342.users.User;

public class UserCatalog {
    private List<User> users;
    private static UserCatalog instance;

    private UserCatalog() {
        this.users = new ArrayList<>();
    }

    public static UserCatalog getInstance() {
        if (instance == null) {
            instance = new UserCatalog();
        }
        return instance;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User findByCredentials(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
