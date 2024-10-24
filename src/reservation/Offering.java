package reservation;

import location.Location;
import location.Space;
import users.Instructor;

public class Offering {
    private Lesson lesson;
    private int capacity;
    private Space space;
    private TimeSlot timeslot;

    private Instructor instructor = null;

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