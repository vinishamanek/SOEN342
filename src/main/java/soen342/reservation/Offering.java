package soen342.reservation;

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

    @Transient
    private Space space;
    @Transient
    private TimeSlot timeslot;

    @ManyToOne(optional = true)
    private Instructor instructor;

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

}