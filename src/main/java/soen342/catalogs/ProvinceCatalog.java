package soen342.catalogs;

import java.util.List;
import soen342.location.Province;

public class ProvinceCatalog {
    private List<Province> provinces;
    private static ProvinceCatalog instance;

    private ProvinceCatalog(List<Province> provinces) {
        this.provinces = provinces;
    }

    public static ProvinceCatalog getInstance(List<Province> provinces) {
        if (instance == null) {
            instance = new ProvinceCatalog(provinces);
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
