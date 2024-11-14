package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.location.Location;

// @note: nothing is using this catalog yet
// do we even need this

public class LocationCatalog {
    private List<Location> locations;

    public LocationCatalog(List<Location> locations) {
        this.locations = locations != null ? locations : new ArrayList<>();
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
