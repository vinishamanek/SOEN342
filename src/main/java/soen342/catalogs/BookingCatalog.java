package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.reservation.Booking;

public class BookingCatalog {
    private List<Booking> bookings;
    private static BookingCatalog instance;

    private BookingCatalog() {
        this.bookings = new ArrayList<>();
    }

    public static BookingCatalog getInstance() {
        if (instance == null) {
            instance = new BookingCatalog();
        }
        return instance;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
