// admin implemented as singleton class for now because only 1 admin allowed

public class Admin extends User {

    private static Admin instance = null;

    private Admin(String email, String password, Organization organization) {
        super(email, password, organization);
    }

    public static Admin getInstance(String email, String password, Organization organization) {
        if (instance == null) {
            instance = new Admin(email, password, organization);
        }
        return instance;
    }

    public void createOffering(Lesson lesson, int capacity, Location location, TimeSlot timeslot) {
        this.getOrganization().createOffering(lesson, capacity, location, timeslot);
    }

    public void deleteAccount(User user) {
        // admin can delete account of instructor or client
        // method will be defined later on
        // System.out.println("Account deleted for: " + user.getEmail());
    }

}