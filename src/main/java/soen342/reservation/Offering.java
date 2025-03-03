package soen342.reservation;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import soen342.location.Location;
import soen342.location.Space;
import soen342.users.Instructor;

@Entity
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Space space;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private TimeSlot timeslot;

    @ManyToOne(optional = true)
    private Instructor instructor;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public Offering() {
    }

    public Offering(Lesson lesson, int capacity, Space space, TimeSlot timeslot) {
        this.lesson = lesson;
        this.capacity = capacity;
        this.space = space;
        this.timeslot = timeslot;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public int getCapacity() {
        return capacity;
    }

    public Space getSpace() {
        return space;
    }

    public Location getLocation() {
        return space.getLocation();
    }

    public TimeSlot getTimeSlot() {
        return timeslot;
    }

    @Override
    public String toString() {
        return "Lesson: " + lesson.getName() + "\n" +
                "Capacity: " + capacity + "\n" +
                "Location: " + space.getName() + ", " + space.getLocation().getName() + ", "
                + space.getLocation().getCity().getName()
                + "\n" +
                "Day: " + timeslot.getDayOfWeek() + "\n" +
                "Start Time: " + timeslot.getStartTime() + "\n" +
                "End Time: " + timeslot.getEndTime();
    }

    public void assignInstructor(Instructor instructor) {
        if (this.hasInstructor()) {
            throw new IllegalStateException("Instructor already assigned to this offering.");
        }
        this.instructor = instructor;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public boolean hasInstructor() {
        return instructor != null;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

}