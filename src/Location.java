import java.util.ArrayList;
import java.util.List;

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

    // hardcoded locations that will be removed later on
    public static List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();

        Province province1 = new Province("Quebec");

        City city1 = new City("Montreal", province1);
        City city2 = new City("Quebec City", province1);

        locations.add(new Location("EV-Building", city1, "123 Happy St"));
        locations.add(new Location("ULaval", city2, "123 Also Happy St"));

        return locations;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        schedule.addTimeSlot(timeSlot);
    }
}
