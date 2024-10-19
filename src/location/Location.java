package location;

import java.util.ArrayList;
import java.util.List;

import reservation.Schedule;
import reservation.TimeSlot;

public class Location {
    private String name;
    private City city;
    private String address;
    private List<Space> spaces;
    private Schedule schedule;

    public Location(String name, City city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.spaces = new ArrayList<>();
        this.schedule = new Schedule();
    }

    public void addSpace(Space space) {
        spaces.add(space);
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        schedule.addTimeSlot(timeSlot);
    }
}
