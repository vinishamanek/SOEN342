
import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {

    private String[] availableCities; // list or array? tbd
    private String specialization; // if can have more than one, make array or list
    private List<Offering> assignedOfferings; 

    public Instructor(String email, String password, String[] availableCities, String specializations) {
        super(email, password);
        this.availableCities = availableCities;
        this.specialization = specialization;
        this.assignedOfferings = new ArrayList<>(); 

    }

    public String getSpecialization() {
        return specialization;
    }

    public String[] getAvailableCities() {
        return availableCities;
    }

    public List<Offering> getAssignedOfferings() {
        return assignedOfferings; 
    }
    
    public void viewAvailableInstructorOfferings() {
        List<Offering> offerings = Offering.getOfferings(); 
        if (offerings.isEmpty()) {
            System.out.println("No offerings available.");
            return;
        }
    
        System.out.println("Available Offerings:");
        int number = 1; 
        for (Offering offering : offerings) {
            System.out.println("Offering Number: " + number++); 
            System.out.println("Lesson: " + offering.getLesson().getName());
            System.out.println("Capacity: " + offering.getCapacity());
            System.out.println("Location: " + offering.getLocation().getName() + ", " + offering.getLocation().getCity().getName());
            System.out.println("Day: " + offering.getTimeSlot().getDayOfWeek());
            System.out.println("Start Time: " + offering.getTimeSlot().getStartTime());
            System.out.println("End Time: " + offering.getTimeSlot().getEndTime());
            System.out.println("-----------------------------------");
        }
    }
    
    public void selectOffering(Offering offering) {
        if (offering == null) {
            System.out.println("Invalid offering. Please select a valid offering.");
            return; 
        }

        assignedOfferings.add(offering);
        System.out.println("You have selected the following offering:");
        System.out.println("Lesson: " + offering.getLesson().getName());
        System.out.println("Capacity: " + offering.getCapacity());
        System.out.println("Location: " + offering.getLocation().getName() + ", " + offering.getLocation().getCity().getName());
        System.out.println("Day: " + offering.getTimeSlot().getDayOfWeek());
        System.out.println("Start Time: " + offering.getTimeSlot().getStartTime());
        System.out.println("End Time: " + offering.getTimeSlot().getEndTime());
    }

}