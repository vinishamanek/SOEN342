import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Offering {
    private Lesson lesson;
    private int capacity;
    private Location location;
    private TimeSlot timeslot;

    public Offering(Lesson lesson, int capacity, Location location, TimeSlot timeslot) {
        this.lesson = lesson;
        this.capacity = capacity;
        this.location = location;
        this.timeslot = timeslot;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public int getCapacity() {
        return capacity;
    }

    public Location getLocation() {
        return location;
    }

    public TimeSlot getTimeSlot() {
        return timeslot;
    }

    // hardcoded offerings that will be removed later on
    public static List<Offering> getOfferings() {
        List<Offering> offerings = new ArrayList<>();

        Lesson lesson1 = new Lesson("Swimming");
        Lesson lesson2 = new Lesson("Judo");

        List<Location> locations = Location.getLocations(); 
        Location location1 = locations.get(0); 
        Location location2 = locations.get(1); 
        
        TimeSlot timeslot1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot timeslot2 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(12, 30));

        offerings.add(new Offering(lesson1, 15, location1, timeslot1));
        offerings.add(new Offering(lesson2, 20, location2, timeslot2));

        return offerings;
    }

    @Override
    public String toString() {
        return "Offering Details: \n" +
               "Lesson: " + lesson.getName() + "\n" +
               "Capacity: " + capacity + "\n" +
               "Location: " + location.getName() + ", " + location.getCity().getName() + "\n" +
               "Day: " + timeslot.getDayOfWeek() + "\n" +
               "Start Time: " + timeslot.getStartTime() + "\n" +
               "End Time: " + timeslot.getEndTime();
    }
    
}