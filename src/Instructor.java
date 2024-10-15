public class Instructor extends User {

    private String[] availableCities;
    private String specialization; // if can have more than one, make array

    public Instructor(String email, String password, String[] availableCities, String specializations) {
        super(email, password);
        this.availableCities = availableCities;
        this.specialization = specialization;

    }

    public String getSpecialization() {
        return specialization;
    }

    public String[] getAvailableCities() {
        return availableCities;
    }

    public void viewAvailableInstructorOfferings() {
        // display offering
    }

    public void selectOffering() {
        // offering as parameter
    }

}