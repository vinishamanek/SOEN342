package soen342.reservation;

import jakarta.persistence.*;
import soen342.users.Client;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; 

    @ManyToOne
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

    protected Booking() {} 

    public Booking(Client client, Offering offering) {
        this.client = client;
        this.offering = offering;
    }

    public Client getClient() {
        return client;
    }

    public Offering getOffering() {
        return offering;
    }

    @Override
    public String toString() {
        return "Booking for Client: " + client.getEmail() + "\n" +
               "Offering: " + offering.toString();
    }
}
