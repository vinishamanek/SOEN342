package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.location.Location;

public class LocationCatalog {
    private static LocationCatalog instance;
    private List<Location> locations;

    private LocationCatalog() {
        this.locations = new ArrayList<>();
    }

    public static LocationCatalog getInstance() {
        if (instance == null) {
            instance = new LocationCatalog();
        }
        return instance;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
