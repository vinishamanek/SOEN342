package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;
import soen342.location.City;

public class CityCatalog {
    private static CityCatalog instance;
    private List<City> cities;
    
    private CityCatalog() {
        this.cities = new ArrayList<>();
    }

    public static CityCatalog getInstance() {
        if (instance == null) {
            instance = new CityCatalog();
        }
        return instance;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }
}
