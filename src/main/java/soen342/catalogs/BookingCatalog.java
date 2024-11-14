package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.reservation.Booking;

public class BookingCatalog {
    private List<Booking> bookings;
    private static BookingCatalog instance;

    private BookingCatalog(List<Booking> bookings) {
        this.bookings = bookings != null ? bookings : new ArrayList<>();
    }

    public static BookingCatalog getInstance(List<Booking> bookings) {
        if (instance == null) {
            instance = new BookingCatalog(bookings);
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
