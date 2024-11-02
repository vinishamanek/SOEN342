package soen342.location;

import soen342.reservation.Schedule;
import soen342.reservation.TimeSlot;

public class Space {
    private String name;
    private int capacity;
    private Location location;
    private Schedule schedule;

    public Space(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;

        this.schedule = new Schedule();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        schedule.addTimeSlot(timeSlot);
    }
}
