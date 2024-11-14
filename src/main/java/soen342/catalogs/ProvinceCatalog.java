package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.location.Province;

public class ProvinceCatalog {
    private List<Province> provinces;
    private static ProvinceCatalog instance;

    private ProvinceCatalog(List<Province> provinces) {
        this.provinces = provinces;
    }

    public static ProvinceCatalog getInstance() {
        if (instance == null) {
            instance = new ProvinceCatalog(new ArrayList<Province>());
        }
        return instance;
    }


    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<Province> getProvinces() {
        return provinces;
    }
}
