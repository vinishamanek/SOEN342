package soen342.location;

import jakarta.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Province province;

    protected City() {
    }

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
