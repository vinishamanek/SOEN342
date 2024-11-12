package soen342.users;
// need to add stuff for minors

import soen342.database.BookingMapper;
import soen342.database.UserMapper;
import soen342.location.Organization;
import soen342.reservation.Offering;
import java.util.List;

import jakarta.persistence.*;

import java.util.ArrayList;
import soen342.reservation.Booking;

@Entity
@Table(name = "clients")
public class Client extends User {

    // @Transient
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @Column(nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private Client guardian; // used for the constructor for underage clients

    @OneToMany(mappedBy = "guardian", cascade = CascadeType.ALL)
    private List<Client> underageClients = new ArrayList<>();

    protected Client() {
        super();
    }

    public Client(String email, String password, Organization organization, int age) {
        super(email, password, organization);
        this.age = age;
    }

    // constructor to creating underage clients
    public Client(String email, Organization organization, int age, Client guardian) {
        super(email, "defaultPassword", organization);
        if (age >= 18) {
            throw new IllegalArgumentException("constructor for underage clients");
        }
        this.age = age;
        this.setGuardian(guardian);
    }

    public boolean isAdult() {
        return age >= 18;
    }

    public boolean canLogin() {
        return isAdult();
    }

    public void addUnderageClient(Client underageClient) {
        if (underageClient.age >= 18) {
            throw new IllegalArgumentException("Underage client must be under 18 years old");
        }
        this.underageClients.add(underageClient);
    }

    public void setGuardian(Client guardian) {
        if (guardian == null || !guardian.isAdult()) {
            throw new IllegalArgumentException("Guardian must be an adult");
        }
        this.guardian = guardian;
    }

    public Client getGuardian() {
        return guardian;
    }

    public List<Client> getUnderageClients() {
        return underageClients;
    }

    public int getAge() {
        return age;
    }

    public List<Offering> getAvailableClientOfferings() {
        List<Offering> offerings = this.getOrganization().getAvailableClientOfferings(this);
        return offerings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void createBooking(Offering offering) {
        Booking booking = new Booking(this, offering);
        bookings.add(booking);
        System.out.println("Booking created for offering: " + offering.toString() + " for client: " + this.getEmail());
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("You have no current bookings.");
        } else {
            System.out.println("\nYour current bookings:");
            for (int i = 0; i < bookings.size(); i++) {
                Booking booking = bookings.get(i);
                System.out.println((i + 1) + ". " + booking.toString());
            }
        }

        if (!underageClients.isEmpty()) {
            for (Client underageClient : underageClients) {
                System.out.println(
                        "\nBookings for " + underageClient.getEmail() + " (age: " + underageClient.getAge() + "):");
                if (underageClient.getBookings().isEmpty()) {
                    System.out.println("No current bookings.");
                } else {
                    for (int i = 0; i < underageClient.getBookings().size(); i++) {
                        Booking booking = underageClient.getBookings().get(i);
                        System.out.println((i + 1) + ". " + booking.toString());
                    }
                }
            }
        }
    }

    public void cancelBooking(Booking booking) {
        Booking bookingToRemove = null;
        for (Booking currentBooking : bookings) {
            if (currentBooking.equals(booking)) {
                bookingToRemove = booking;
                break;
            }
        }
        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove);
            System.out.println("Booking canceled for offering: " + bookingToRemove.getOffering().toString());
        } else {
            System.out.println("No booking found for the offering specified");
        }
    }

}
