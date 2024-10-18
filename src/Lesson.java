import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private String name;
    private List<Offering> offerings = new ArrayList<Offering>();

    public Lesson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addOffering(int capacity, Location location, TimeSlot timeSlot) {
        Offering offering = new Offering(this, capacity, location, timeSlot);
        offerings.add(offering);
    }

    public List<Offering> getOfferings() {
        return offerings;
    }
}
