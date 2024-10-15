// admin implemented as singleton class for now because only 1 admin allowed

public class Admin extends User {

    private static Admin instance = null;

    private Admin(String email, String password) { 
        super(email, password);
    }

    public static Admin getInstance(String email, String password) {
        if (instance == null) {
            instance = new Admin(email, password);
        }
        return instance;
    }

    public void createOffering(Lesson lesson, int capacity, Location location, TimeSlot timeslot) {
        Offering offering = new Offering(lesson, capacity, location, timeslot);
        System.out.println("Offering created successfully:");
        System.out.println(offering);
    }

    public void deleteAccount(User user) {
        // admin can delete account of instructor or client
        // method will be defined later on
        // System.out.println("Account deleted for: " + user.getEmail());
    }

}