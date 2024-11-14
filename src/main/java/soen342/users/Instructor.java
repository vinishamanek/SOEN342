package soen342.users;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import soen342.location.City;
import soen342.location.Organization;
import soen342.reservation.Offering;

@Entity
@Table(name = "instructors")
public class Instructor extends User {

    @ManyToMany
    @JoinTable(
    name = "instructor_cities",
    joinColumns = @JoinColumn(name = "instructor_id"),
    inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private List<City> availableCities;

    @Column(nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "instructor")
    private List<Offering> offerings;

    @Column(nullable = false)
    private String phoneNumber;

    protected Instructor() {
        super();
    }

    public Instructor(String email, String password, String phoneNumber, Organization organization, List<City> availableCities,
            String specialization) {
        super(email, password, organization);
        this.phoneNumber = phoneNumber;
        this.availableCities = availableCities;
        this.specialization = specialization;
        this.offerings = new ArrayList<>();

    }

    public String getSpecialization() {
        return specialization;
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public List<Offering> getAssignedOfferings() {
        return offerings;
    }

    public List<Offering> getAvailableInstructorOfferings() {
        List<Offering> offerings = this.getOrganization().getAvailableInstructorOfferings(this);
        return offerings;
    }

    public void selectOffering(Offering offering) {
        if (offering == null) {
            throw new IllegalArgumentException("Offering cannot be null");
        }
        offering.assignInstructor(this);
        offerings.add(offering);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}