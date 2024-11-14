package soen342.catalogs;

import java.util.List;
import java.util.ArrayList;
import soen342.users.User;

public class UserCatalog {
    private List<User> users;

    public UserCatalog(List<User> users) {
        this.users = users != null ? users : new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
