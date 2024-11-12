package soen342.users;

import jakarta.persistence.*;
import soen342.location.Organization;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, unique = true)
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

    protected Organization getOrganization() {
        return this.organization;
    }

    public String toString() {
        return this.email + " (" + this.getClass().getSimpleName() + ")";
    }

}
