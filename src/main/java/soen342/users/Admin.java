package soen342.users;
// admin implemented as singleton class for now because only 1 admin allowed

import soen342.location.Space;
import jakarta.persistence.*;
import soen342.location.Organization;
import soen342.reservation.Lesson;
import soen342.reservation.TimeSlot;

@Entity
@Table(name = "admins")
public class Admin extends User {

    private static Admin instance = null;

    protected Admin() {
        super();
    }

    private Admin(String email, String password, Organization organization) {
        super(email, password, organization);
    }

    public static Admin getInstance(String email, String password, Organization organization) {
        if (instance == null) {
            instance = new Admin(email, password, organization);
        }
        return instance;
    }

    public void deleteAccount(User user) {
        // admin can delete account of instructor or client
        // method will be defined later on
        // System.out.println("Account deleted for: " + user.getEmail());
    }

}