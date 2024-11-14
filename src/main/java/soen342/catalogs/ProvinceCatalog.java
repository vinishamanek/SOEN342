package soen342.catalogs;

import java.util.List;

import soen342.location.Province;

public class ProvinceCatalog {
    private List<Province> provinces;

    public ProvinceCatalog(List<Province> provinces) {
        this.provinces = provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }
}
