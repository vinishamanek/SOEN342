package soen342.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import soen342.reservation.Booking;
import soen342.reservation.Lesson;
import soen342.reservation.Offering;
import soen342.users.Instructor;
import soen342.users.Client;

@Entity
public class Organization {

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

    public Organization(String name) {
        this.name = name;
    }

    public List<Offering> getAvailableInstructorOfferings(Instructor instructor) {
        List<Offering> offerings = new ArrayList<Offering>();
        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                if (instructor.getAvailableCities().contains(offering.getLocation().getCity())
                        && !offering.hasInstructor()) {
                    offerings.add(offering);
                }
            }
        }
        return offerings;
    }

    public List<Offering> getAvailableClientOfferings(Client client) {
        List<Offering> availableOfferings = new ArrayList<>();
        Set<Offering> bookedOfferings = new HashSet<>();

        if (client != null) {
            List<Booking> clientBookings = client.getBookings();
            for (Booking booking : clientBookings) {
                bookedOfferings.add(booking.getOffering());
            }
        }

        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                int bookingCount = offering.getBookings().size();
                if (offering.getCapacity() > bookingCount && !bookedOfferings.contains(offering) && offering.hasInstructor()) {
                    availableOfferings.add(offering);
                } else if (client == null && offering.hasInstructor() && !bookedOfferings.contains(offering)) {
                    availableOfferings.add(offering);
                }
            }
        }
        return availableOfferings;
    }

    // public List<Offering> getPublicOfferings() {
    // List<Offering> offerings = new ArrayList<Offering>();
    // for (Lesson lesson : Lessons) {
    // for (Offering offering : lesson.getOfferings()) {
    // if (offering.hasInstructor()) {
    // offerings.add(offering);
    // }
    // }
    // }
    // return offerings;

    // }

    public void addLesson(Lesson lesson) {
        Lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return Lessons;
    }

    public void addOwnedSpace(Space space) {
        ownedSpaces.add(space);
    }

    public List<Space> getOwnedSpaces() {
        return ownedSpaces;
    }

    public Lesson getLastLesson() {
        return Lessons.get(Lessons.size() - 1);
    }

}
