package reservation;

import java.util.ArrayList;
import java.util.List;

import location.Space;

public class Lesson {
    private String name;
    private List<Offering> offerings = new ArrayList<Offering>();

    public Lesson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addOffering(int capacity, Space space, TimeSlot timeSlot) {
        Offering offering = new Offering(this, capacity, space, timeSlot);
        offerings.add(offering);
    }

    public List<Offering> getOfferings() {
        return offerings;
    }
}
