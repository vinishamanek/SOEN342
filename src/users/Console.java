package users;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import location.Location;
import reservation.Lesson;
import reservation.Offering;
import reservation.TimeSlot;

public class Console {

    private User user;
    private Scanner scanner;

    public Console(User user) {
        this.setUser(user);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        if (this.user == null) {
            this.login();
        }
        if (this.user instanceof Admin) {
            adminMenu();
        } else if (this.user instanceof Instructor) {
            instructorMenu();
        } else if (this.user instanceof Client) {
            clientMenu();
        } else {
            throw new RuntimeException("Role not recognized.");
        }
    }

    private void login() {
        while (true) {
            System.out.println("What is your email?");
            String email = prompt();
            System.out.println("What is your password?");
            String password = prompt();
            this.setUser(User.login(email, password));
            if (this.user != null) {
                System.out.println("Welcome " + user.getEmail());
                return;
            }
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("c: create offering | e: logout");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'c':
                    System.out.println("Enter lesson name: ");
                    String lessonName = prompt();

                    System.out.println("Enter capacity: ");
                    int capacity = Integer.parseInt(prompt());

                    List<Location> locations = this.user.getOrganization().getOwnedSpaces();
                    System.out.println("Available Locations:");
                    for (int i = 0; i < locations.size(); i++) {
                        System.out.println(
                                (i + 1) + ". " + locations.get(i).getName() + ", "
                                        + locations.get(i).getCity().getName());
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
                    System.out.println("Offering created successfully:");
                    break;
                case 'e':
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid operation. Please enter 'c' or 'e'.");
                    break;
            }
        }
    }

    // parse String input to LocalTime used for TimeSlot (startTime and endTime)
    private LocalTime parseTime(String timeInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", java.util.Locale.US);

        while (true) {
            try {
                timeInput = timeInput.trim().toUpperCase();

                LocalTime time = LocalTime.parse(timeInput, formatter);
                return time;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please enter the time in the format 'h:mm AM/PM'.");
                System.out.print("Enter time again (e.g., 9:00 AM): ");
                timeInput = scanner.nextLine().trim();
            }
        }
    }

    private void instructorMenu() {
        Instructor instructor = (Instructor) this.user;
        while (true) {
            System.out.println("v: view available offerings | s: select offering | e: logout");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'v':
                    List<Offering> availableOfferings = instructor.getAvailableInstructorOfferings();
                    listOfferings(availableOfferings);
                    break;
                case 's':
                    List<Offering> offerings = instructor.getAvailableInstructorOfferings();
                    if (offerings.isEmpty()) {
                        System.out.println("No offerings available to select.");
                        continue;
                    }
                    System.out.println("Available Offerings:");
                    for (int i = 0; i < offerings.size(); i++) {
                        System.out.println((i + 1) + ". " + offerings.get(i).getLesson().getName());
                    }
                    System.out.println("Please enter the number of the offering you'd like to select:");
                    int selectedNumber = 0;
                    while (true) {
                        selectedNumber = Integer.parseInt(prompt());
                        if (selectedNumber >= 1 && selectedNumber <= offerings.size())
                            break;
                        System.out.println(
                                "Invalid selection. Please select a number between 1 and " + offerings.size() + ".");
                    }
                    Offering selectedOffering = offerings.get(selectedNumber - 1);
                    instructor.selectOffering(selectedOffering);
                    System.out.println("You have selected the following offering:");
                    System.out.println(selectedOffering);
                    break;
                case 'e':
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid operation. Please enter 'v', 's', or 'e'.");
                    break;
            }
        }
    }

    private void clientMenu() {
        Client client = (Client) this.user;
        while (true) {
            System.out.println("o: view offerings | m: make booking | b: view bookings | e: logout");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'o':
                    List<Offering> availableOfferings = client.getAvailableClientOfferings();
                    listOfferings(availableOfferings);
                    break;

                case 'm':
                    System.out.println("This function is not yet implemented.");
                    break;
                case 'b':
                    System.out.println("This function is not yet implemented.");
                    break;
                case 'e':
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid operation. Please enter 'o', 'm', 'b', or 'e'.");
                    break;
            }
        }
    }

    private void listOfferings(List<Offering> offerings) {
        if (offerings.isEmpty()) {
            System.out.println("No offerings available.");
        } else {
            System.out.println("Available Offerings:");
            int number = 1;
            for (Offering offering : offerings) {
                System.out.println("Offering Number: " + number++);
                System.out.println(offering);
                System.out.println("-----------------------------------");
            }
        }
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

    private void setUser(User user) {
        this.user = user;
    }

}
