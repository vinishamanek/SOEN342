package soen342.users;

import jakarta.persistence.*;
import soen342.location.Organization;

@Entity
@Table(name = "admins")
public class Admin extends User {

    protected Admin() {
        super();
    }

    public Admin(String email, String password, Organization organization) {
        super(email, password, organization);
    }

}