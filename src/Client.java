// need to add stuff for minors

public class Client extends User {

    public Client(String email, String password, Organization organization) {
        super(email, password, organization);
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
