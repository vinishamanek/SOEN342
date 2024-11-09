package soen342.location;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import soen342.reservation.Lesson;
import soen342.reservation.Offering;
import soen342.reservation.TimeSlot;
import soen342.users.Instructor;
import soen342.users.Client;

@Entity
public class Organization {
    public static Organization instance;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    public String name;

    @OneToMany
    @JoinTable(name = "organization_owned_spaces", joinColumns = @JoinColumn(name = "organization_id"), inverseJoinColumns = @JoinColumn(name = "owned_space_id"))
    public List<Space> ownedSpaces = new ArrayList<Space>();

    @OneToMany
    @JoinTable(name = "organization_rented_spaces", joinColumns = @JoinColumn(name = "organization_id"), inverseJoinColumns = @JoinColumn(name = "rented_space_id"))
    public List<Space> rentedSpaces = new ArrayList<Space>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    public List<Lesson> Lessons = new ArrayList<Lesson>();

    protected Organization() {
    }

    private Organization(String name) {
        this.name = name;
    }

    public static Organization getInstance(String name) {
        if (instance == null) {
            instance = new Organization(name);
        }
        return instance;
    }

    public static Organization getInstance() {
        return Organization.getInstance("Organization");
    }

    public Offering createOffering(Lesson lesson, int capacity, Space space, TimeSlot timeslot) {
        space.addTimeSlot(timeslot);
        return lesson.addOffering(capacity, space, timeslot);
    }

    public List<Offering> getAvailableInstructorOfferings(Instructor instructor) {
        List<Offering> offerings = new ArrayList<Offering>();
        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                if (instructor.getAvailableCities().contains(offering.getLocation().getCity())) {
                    offerings.add(offering);
                }
            }
        }
        return offerings;
    }

    // todo: take the cient's details & existing bookings into account
    public List<Offering> getAvailableClientOfferings(Client client) {
        List<Offering> offerings = new ArrayList<Offering>();
        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                if (offering.hasInstructor()) {
                    offerings.add(offering);
                }
            }
        }
        return offerings;
    }

    public List<Offering> getPublicOfferings() {
        List<Offering> offerings = new ArrayList<Offering>();
        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                if (offering.hasInstructor()) {
                    offerings.add(offering);
                }
            }
        }
        return offerings;

    }

    public void addLesson(Lesson lesson) {
        Lessons.add(lesson);
    }

    public void addOwnedSpace(Space space) {
        ownedSpaces.add(space);
    }

    public List<Space> getOwnedSpaces() {
        return ownedSpaces;
    }

}
