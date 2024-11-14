package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.location.Location;

public class LocationCatalog {
    private static LocationCatalog instance;
    private List<Location> locations;

    private LocationCatalog(List<Location> locations) {
        this.locations = locations != null ? locations : new ArrayList<>();
    }

    public static LocationCatalog getInstance(List<Location> locations) {
        if (instance == null) {
            instance = new LocationCatalog(locations);
        }
        return instance;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
