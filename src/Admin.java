// unsure about all of this, but i did a singleton sort of vibe since only 1 admin

public class Admin extends User {

    private static Admin instance = null;

    private Admin(String email, String password) { // made constructor private for now since only one should be created?
        super(email, password);
    }

    public static Admin getInstance(String email, String password) {
        if (instance == null) {
            instance = new Admin(email, password);
        }
        return instance;
    }

    public void createOffering() {
        // lesson, capacity, location, timeslot
    }

    public void deleteAccount(User user) {
        // delete account
        // System.out.println("Account deleted for: " + user.getEmail());
    }

}