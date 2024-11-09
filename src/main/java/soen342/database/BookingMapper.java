package soen342.database;

import jakarta.persistence.TypedQuery;
import java.util.List;
import soen342.reservation.Booking;
import soen342.reservation.Offering;
import soen342.users.Client;

public class BookingMapper extends AbstractMapper<Booking> {

    public List<Booking> findByClient(Client client) {
        TypedQuery<Booking> query = entityManager.createQuery(
                "SELECT b FROM Booking b WHERE b.client = :client", Booking.class);
        query.setParameter("client", client);
        return query.getResultList();
    }

    public List<Booking> findByOffering(Offering offering) {
        TypedQuery<Booking> query = entityManager.createQuery(
                "SELECT b FROM Booking b WHERE b.offering = :offering", Booking.class);
        query.setParameter("offering", offering);
        return query.getResultList();
    }

    public List<Booking> findAll() {
        TypedQuery<Booking> query = entityManager.createQuery(
                "SELECT b FROM Booking b", Booking.class);
        return query.getResultList();
    }
}
