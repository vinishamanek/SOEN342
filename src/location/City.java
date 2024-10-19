package location;

public class City {

    private String name;
    private Province province;

    public City(String name, Province province) {
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public Province getProvince() {
        return province;
    }

}
