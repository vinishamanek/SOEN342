package soen342.users;

import jakarta.persistence.*;
import soen342.location.Organization;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    private static User[] users;

    public static void setUsers(User[] users) {
        User.users = users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Organization organization;

    protected User() {
    }

    public User(String email, String password, Organization organization) {
        this.email = email;
        this.password = password;
        this.organization = organization;
    }

    public String getEmail() {
        return this.email;
    }

    public static User login(String email, String password) {
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    protected Organization getOrganization() {
        return this.organization;
    }

}
