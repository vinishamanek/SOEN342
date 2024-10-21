package users;
// need to add stuff for minors

import location.Organization;
import reservation.Offering;
import java.util.List;

public class Client extends User {

    public Client(String email, String password, Organization organization) {
        super(email, password, organization);
    }

    public List<Offering> getAvailableClientOfferings() {
        List<Offering> offerings = this.getOrganization().getAvailableClientOfferings(this);
        return offerings;
    }

    public void makeBooking(String offering) {
        // client can book an offering
        // method will be defined later on
    }

    public void cancelBooking(String offering) {
        // client can cancel a booking they've made
        // method will be defined later on
    }
}
