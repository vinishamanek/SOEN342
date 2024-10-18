import java.util.ArrayList;
import java.util.List;

public class Organization {

    public String name;

    // Todo: change this to spaces
    public List<Location> ownedSpaces = new ArrayList<Location>();
    public List<Location> rentedSpaces = new ArrayList<Location>();
    public List<Lesson> Lessons = new ArrayList<Lesson>();

    public Organization(String name) {
        this.name = name;
    }

    public void createOffering(Lesson lesson, int capacity, Location location, TimeSlot timeslot) {
        location.addTimeSlot(timeslot);
        lesson.addOffering(capacity, location, timeslot);
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

    public void addLesson(Lesson lesson) {
        Lessons.add(lesson);
    }

    public void addOwnedSpace(Location location) {
        ownedSpaces.add(location);
    }

    public List<Location> getOwnedSpaces() {
        return ownedSpaces;
    }

}
