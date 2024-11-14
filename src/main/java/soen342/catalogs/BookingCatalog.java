package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.reservation.Booking;

public class BookingCatalog {
    private List<Booking> bookings;

    public BookingCatalog(List<Booking> bookings) {
        this.bookings = bookings != null ? bookings : new ArrayList<>();
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
