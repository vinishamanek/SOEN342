import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Console {

    private User user;
    private Scanner scanner;

    public Console(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        if (this.user == null) {
            this.login();
        }
        while (true) {
            String input = prompt();
            if (input.equals("exit")) {
                break;
            }
            System.out.println(input);
        }
    }

    private void login() {
        while (true) {
            System.out.println("What is your email?");
            String email = prompt();
            System.out.println("What is your password?");
            String password = prompt();
            this.user = User.login(email, password);
            System.out.println(user);
            System.out.println("Welcome " + user.getEmail());
            if (this.user != null) {
                if (this.user instanceof Admin) {
                    adminMenu();
                } else if (this.user instanceof Instructor) {
                    instructorMenu();
                } else if (this.user instanceof Client) {
                    clientMenu();
                } else {
                    System.out.println("role not recognized.");
                }
            }
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("Do you want to create an offering? (yes/no)");
            String input = prompt();

            if (input.equalsIgnoreCase("yes")) {
                System.out.println("Enter lesson name: ");
                String lessonName = prompt();

                System.out.println("Enter capacity: ");
                int capacity = Integer.parseInt(prompt());

                List<Location> locations = Location.getLocations();
                System.out.println("Available Locations:");
                for (int i = 0; i < locations.size(); i++) {
                    System.out.println((i + 1) + ". " + locations.get(i).getName() + ", " + locations.get(i).getCity().getName());
                }

                System.out.println("Please enter the number of the location you want to select: ");
                int locationIndex = Integer.parseInt(prompt()) - 1;

                if (locationIndex < 0 || locationIndex >= locations.size()) {
                    System.out.println("Invalid location selection. Please try again.");
                    continue;
                }

                Location selectedLocation = locations.get(locationIndex);

                System.out.println("Enter day of the week (e.g., Monday): ");
                String dayInput = prompt().toUpperCase();
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayInput);

                System.out.println("Enter start time (e.g., 9:00 AM): ");
                String startTimeInput = prompt();
                LocalTime startTime = parseTime(startTimeInput);

                System.out.println("Enter end time (e.g., 11:00 AM): ");
                String endTimeInput = prompt();
                LocalTime endTime = parseTime(endTimeInput);

                Lesson lesson = new Lesson(lessonName);
                TimeSlot timeslot = new TimeSlot(dayOfWeek, startTime, endTime);

                Admin admin = (Admin) this.user;
                admin.createOffering(lesson, capacity, selectedLocation, timeslot);

            } else if (input.equalsIgnoreCase("no")) {
                System.out.println("Returning to main menu...");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    // need to review this method
    // parse String input to LocalTime used for TimeSlot (startTime and endTime)
    public LocalTime parseTime(String timeInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

        try {
            return LocalTime.parse(timeInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter the time in the format 'hh:mm AM/PM'.");
            return null;
        }
    }

    private void instructorMenu() {
        Instructor instructor = (Instructor) this.user;

        while (true) {
            System.out.println("Would you like to view available offerings? (yes/no)");
            String input = prompt();

            if (input.equalsIgnoreCase("yes")) {
                instructor.viewAvailableInstructorOfferings();

                System.out.println("Would you like to select an offering? (yes/no)");
                String selectInput = prompt();
                if (selectInput.equalsIgnoreCase("yes")) {
                    List<Offering> offerings = Offering.getOfferings();

                    if (offerings.isEmpty()) {
                        System.out.println("No offerings available to select.");
                        continue; 
                    }

                    System.out.println("Available Offerings:");
                    for (int i = 0; i < offerings.size(); i++) {
                        System.out.println((i + 1) + ". " + offerings.get(i).getLesson().getName());
                    }

                    System.out.println("Please enter the number of the offering you'd like to select:");
                    int selectedNumber = Integer.parseInt(prompt());

                    if (selectedNumber < 1 || selectedNumber > offerings.size()) {
                        System.out.println("Invalid selection. Please select a number between 1 and " + offerings.size() + ".");
                        continue; 
                    }

                    Offering selectedOffering = offerings.get(selectedNumber - 1);
                    instructor.selectOffering(selectedOffering); 

                } else {
                    System.out.println("Returning to the instructor menu...");
                }

            } else if (input.equalsIgnoreCase("no")) {
                System.out.println("Exiting instructor menu...");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    private void clientMenu() {
        // book offerings
        // cancel a booking
        // etc...
    }

    private String prompt() {
        return this.prompt("> ");
    }

    private String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public void cleanup() {
        scanner.close();
    }

}
