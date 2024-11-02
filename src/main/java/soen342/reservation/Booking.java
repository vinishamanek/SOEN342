package soen342.reservation;

import soen342.users.Client;

public class Booking {

    private Client client;
    private Offering offering;

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