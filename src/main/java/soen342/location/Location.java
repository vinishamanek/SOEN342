package soen342.location;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private City city;

    private String address;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Space> spaces = new ArrayList<>();

    protected Location() {
    }

    public Location(String name, City city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public void addSpace(Space space) {
        space.setLocation(this);
        spaces.add(space);
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

}

// package soen342.location;

// import java.util.ArrayList;
// import java.util.List;

// public class Location {
// private String name;
// private City city;
// private String address;
// private List<Space> spaces;

// public Location(String name, City city, String address) {
// this.name = name;
// this.city = city;
// this.address = address;
// this.spaces = new ArrayList<>();
// }

// public void addSpace(Space space) {
// space.setLocation(this);
// spaces.add(space);
// }

// public String getName() {
// return name;
// }

// public City getCity() {
// return city;
// }

// public String getAddress() {
// return address;
// }

// public List<Space> getSpaces() {
// return spaces;
// }
// }
