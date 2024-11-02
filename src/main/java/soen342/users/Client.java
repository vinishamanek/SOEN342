package soen342.users;
// need to add stuff for minors

import soen342.location.Organization;
import soen342.reservation.Offering;
import java.util.List;
import java.util.ArrayList;
import soen342.location.Organization;
import soen342.reservation.Booking;
import soen342.reservation.Offering;

public class Client extends User {
    private List<Booking> bookings = new ArrayList<>();

    public Client(String email, String password, Organization organization) {
        super(email, password, organization);
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
        System.out.println("Booking created for offering: " + offering.toString());
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
        } else {
            System.out.println("Your current bookings:");
            for (int i = 0; i < bookings.size(); i++) {
                Booking booking = bookings.get(i);
                System.out.println((i + 1) + ". " + booking.toString()); // Assumes Booking class has a proper
                                                                         // toString() implementation
            }
        }
    }

    public void cancelBooking(Offering offering) {
        Booking bookingToRemove = null;
        for (Booking booking : bookings) {
            if (booking.getOffering().equals(offering)) {
                bookingToRemove = booking;
                break;
            }
        }
        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove);
            System.out.println("booking cancelled for offering: " + offering.toString());
        } else {
            System.out.println("no booking found for the offering specified");
        }
    }
}
