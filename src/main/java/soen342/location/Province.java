package soen342.location;

import jakarta.persistence.*;

@Entity
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    protected Province() {
    }

    public Province(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
