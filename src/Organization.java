import java.util.ArrayList;
import java.util.List;

public class Organization {

    public String name;

    public List<Space> ownedSpaces = new ArrayList<Space>();
    public List<Space> rentedSpaces = new ArrayList<Space>();
    public List<Lesson> Lessons = new ArrayList<Lesson>();

    public Organization(String name) {
        this.name = name;
    }

    public void createOffering(Lesson lesson, int capacity, Location location, TimeSlot timeslot) {
        location.addTimeSlot(timeslot);
        lesson.addOffering(capacity, location, timeslot);
    }

}
