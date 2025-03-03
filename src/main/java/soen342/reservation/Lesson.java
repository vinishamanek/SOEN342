package soen342.reservation;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import soen342.location.Space;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.PERSIST)
    private List<Offering> offerings = new ArrayList<Offering>();

    public Lesson() {
    }

    public Lesson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Offering addOffering(int capacity, Space space, TimeSlot timeSlot) {
        Offering offering = new Offering(this, capacity, space, timeSlot);
        offerings.add(offering);
        return offering;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public String toString() {
        return name;
    }
}
