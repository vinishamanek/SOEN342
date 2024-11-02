package soen342.location;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private City city;
    private String address;
    private List<Space> spaces;

    public Location(String name, City city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.spaces = new ArrayList<>();
    }

    public void addSpace(Space space) {
        space.setLocation(this);
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
}
