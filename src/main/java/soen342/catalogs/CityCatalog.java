package soen342.catalogs;

import java.util.List;

import soen342.location.City;

public class CityCatalog {
    private List<City> cities;

    public CityCatalog(List<City> cities) {
        this.cities = cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
