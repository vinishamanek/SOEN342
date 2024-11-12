package soen342.users;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import soen342.database.*;
import soen342.location.City;
import soen342.location.Location;
import soen342.location.Organization;
import soen342.location.Space;
import soen342.reservation.Booking;
import soen342.reservation.Lesson;
import soen342.reservation.Offering;
import soen342.reservation.TimeSlot;

public class Console {

    private User user;
    private Scanner scanner;
    private LocationMapper locationMapper;
    private OfferingMapper offeringMapper;
    private OrganizationMapper organizationMapper;
    private SpaceMapper spaceMapper;
    private UserMapper userMapper;

    public Console(User user) {
        this.setUser(user);
        this.scanner = new Scanner(System.in);
        this.locationMapper = new LocationMapper();
        this.offeringMapper = new OfferingMapper();
        this.organizationMapper = new OrganizationMapper();
        this.spaceMapper = new SpaceMapper();
        this.userMapper = new UserMapper();
    }

    public void run() {
        if (this.user == null) {
            this.defaultMenu();
        } else if (this.user instanceof Admin) {
            adminMenu();
        } else if (this.user instanceof Instructor) {
            instructorMenu();
        } else if (this.user instanceof Client) {
            clientMenu();
        } else {
            throw new RuntimeException("Role not recognized.");
        }
    }

    private void defaultMenu() {
        while (true) {
            System.out.println("v: view offerings | l: login | s: signup | e: exit");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'v':
                    Organization organization = this.organizationMapper.getDefault();
                    List<Offering> offerings = organization.getPublicOfferings();
                    listOfferings(offerings);
                    break;
                case 'l':
                    login();
                    run();
                    break;
                case 's':
                    signup();
                    break;
                case 'e':
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid operation. Please enter 'v', 'l', or 'e'.");
                    break;
            }
        }
    }

    private void signup() {
        System.out.println("select which role you wish to sign up as: ");
        System.out.println("1. client");
        System.out.println("2. instructor");

        int roleChoice = Integer.parseInt(prompt());
        Organization organization = this.organizationMapper.getDefault();

        System.out.println("enter email: ");
        String email = prompt();

        System.out.println("enter password: ");
        String password = prompt();

        if (roleChoice == 1) {
            System.out.println("enter age: ");
            int age = Integer.parseInt(prompt());

            Client newClient = new Client(email, password, organization, age);

            userMapper.create(newClient);
            System.out.println("client account created successfully for " + newClient.getEmail() + "!");

        } else if (roleChoice == 2) {
            System.out.println("enter specialization: ");
            String specialization = prompt();

            Set<City> allCitiesSet = new HashSet<>();
            for (Space space : organization.getOwnedSpaces()) {
                Location location = space.getLocation();
                if (location != null && location.getCity() != null) {
                    allCitiesSet.add(location.getCity());
                }
            }
            List<City> allCities = new ArrayList<>(allCitiesSet);

            System.out.println("available cities:");
            for (int i = 0; i < allCities.size(); i++) {
                System.out.println((i + 1) + ". " + allCities.get(i).getName());
            }

            System.out.println("enter city numbers (comma-separated) where you're available to teach: ");
            String[] cityChoices = prompt().split(",");
            List<City> selectedCities = new ArrayList<>();

            for (String choice : cityChoices) {
                try {
                    int index = Integer.parseInt(choice.trim()) - 1;
                    if (index >= 0 && index < allCities.size()) {
                        selectedCities.add(allCities.get(index));
                    } else {
                        System.out.println("city number " + (index + 1) + " is out of range.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("invalid input: " + choice.trim());
                }
            }

            Instructor newInstructor = new Instructor(email, password, organization, selectedCities, specialization);
            userMapper.create(newInstructor);
            System.out.println("instructor account created successfully " + newInstructor.getEmail() + "!");
        }
    }

    private void login() {
        while (true) {
            System.out.println("What is your email?");
            String email = prompt();
            System.out.println("What is your password?");
            String password = prompt();
            User user = this.userMapper.findByCredentials(email, password);
            if (user != null) {
                this.setUser(user);
                System.out.println("Welcome " + user.getEmail());
                return;
            }
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("o: create offering | l: create lesson | e: logout");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'o':
                    System.out.println("All lessons: ");
                    List<Lesson> lessons = this.user.getOrganization().getLessons();
                    Lesson selectedLesson = selectFromItems(lessons);

                    System.out.println("Enter capacity: ");
                    int capacity = Integer.parseInt(prompt());

                    List<Space> spaces = this.user.getOrganization().getOwnedSpaces();
                    System.out.println("Available spaces:");
                    Space selectedSpace = selectFromItems(spaces);

                    System.out.println("Enter day of the week (e.g., Monday): ");
                    String dayInput = prompt().toUpperCase();
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayInput);

                    System.out.println("Enter start time (e.g., 9:00 AM): ");
                    String startTimeInput = prompt();
                    LocalTime startTime = parseTime(startTimeInput);

                    System.out.println("Enter end time (e.g., 11:00 AM): ");
                    String endTimeInput = prompt();
                    LocalTime endTime = parseTime(endTimeInput);

                    TimeSlot timeslot = new TimeSlot(dayOfWeek, startTime, endTime);
                    selectedSpace.addTimeSlot(timeslot);
                    this.locationMapper.update(selectedSpace.getLocation());
                    timeslot = selectedSpace.getLastTimeSlot();
                    Offering offering = selectedLesson.addOffering(capacity, selectedSpace, timeslot);
                    this.offeringMapper.create(offering);

                    System.out.println("Offering created successfully:");
                    break;
                case 'l':
                    System.out.println("Enter lesson name: ");
                    String name = prompt();
                    Lesson newLesson = new Lesson(name);
                    this.user.getOrganization().addLesson(newLesson);
                    this.organizationMapper.update(this.user.getOrganization());
                    System.out.println("Lesson created successfully.");
                    break;
                case 'e':
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid operation. Please enter 'o', 'l', or 'e'.");
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
        List<Offering> availableOfferings;
        BookingMapper bookingMapper = new BookingMapper();

        while (true) {
            System.out
                    .println("o: view offerings | m: make booking | v: view bookings | c: cancel booking | e: logout");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'o':
                    availableOfferings = client.getAvailableClientOfferings();
                    listOfferings(availableOfferings);
                    break;

                case 'm':
                    System.out.println("available offerings to book:");
                    availableOfferings = client.getAvailableClientOfferings();
                    listOfferings(availableOfferings);

                    if (availableOfferings.isEmpty()) {
                        System.out.println("no available offerings to book.");
                        break;
                    }

                    System.out.println("do you want to make a booking for yourself or an underage client? (self/underage)");
                    String bookingFor = prompt().toLowerCase();

                    if (bookingFor.equals("underage")) {
                        System.out.println("do you want to create a new underage client? (yes/no)");
                        String createNew = prompt().toLowerCase();
                        
                        Client targetClient = client;

                        if (createNew.equals("yes")) {
                            System.out.println("enter email for the underage client:");
                            String underageEmail = prompt();
                            System.out.println("enter age for the underage client:");
                            int underageAge = Integer.parseInt(prompt());

                            client.addUnderageClient(underageEmail, underageAge);
                            targetClient = client.getUnderageClients().get(client.getUnderageClients().size() - 1);
                            System.out.println("underage client " + targetClient.getEmail() + " created successfully.");
                        } else {
                            List<Client> underageClients = client.getUnderageClients();
                            if (underageClients.isEmpty()) {
                                System.out.println("no underage clients found. please create one first.");
                                break;
                            }

                            System.out.println("select an underage client:");
                            for (int i = 0; i < underageClients.size(); i++) {
                                System.out.println((i + 1) + ". " + underageClients.get(i).getEmail());
                            }
                            int choice = Integer.parseInt(prompt()) - 1;
                            if (choice >= 0 && choice < underageClients.size()) {
                                targetClient = underageClients.get(choice);
                            } else {
                                System.out.println("invalid selection.");
                                break;
                            }
                        }

                        availableOfferings = targetClient.getAvailableClientOfferings();
                        listOfferings(availableOfferings);

                        if (availableOfferings.isEmpty()) {
                            System.out.println("no available offerings to book.");
                            break;
                        }

                        System.out.println("enter the number associated with the offering you'd like to book:");
                        int offeringIndex = Integer.parseInt(prompt()) - 1;

                        if (offeringIndex >= 0 && offeringIndex < availableOfferings.size()) {
                            Offering selectedOffering = availableOfferings.get(offeringIndex);
                            Booking underageBooking = new Booking(client, selectedOffering);
                            bookingMapper.create(underageBooking);
                            System.out.println("booking created successfully for " + targetClient.getEmail() + "!");
                        } else {
                            System.out.println("error: Invalid offering number.");
                        }
                    } else {
                        availableOfferings = client.getAvailableClientOfferings();
                        listOfferings(availableOfferings);

                        if (availableOfferings.isEmpty()) {
                            System.out.println("no available offerings to book.");
                            break;
                        }

                        System.out.println("enter the number associated with the offering you'd like to book:");
                        int offeringIndex = Integer.parseInt(prompt()) - 1;

                        if (offeringIndex >= 0 && offeringIndex < availableOfferings.size()) {
                            Offering selectedOffering = availableOfferings.get(offeringIndex);
                            
                            Booking booking = new Booking(client, selectedOffering);
                            bookingMapper.create(booking);

                            client.createBooking(selectedOffering);
                            System.out.println("Booking created successfully!");
                        } else {
                            System.out.println("Error: Invalid offering number.");
                        }
                    }
                    break;
                case 'v':
                    client.viewBookings();
                    break;
                case 'c':
                    client.viewBookings();
                    if (!client.getBookings().isEmpty()) {
                        System.out.println("Enter the number associated with the booking you'd like to cancel:");

                        if (scanner.hasNextInt()) {
                            int bookingIndex = scanner.nextInt();
                            scanner.nextLine();

                            if (bookingIndex > 0 && bookingIndex <= client.getBookings().size()) {
                                Booking bookingToCancel = client.getBookings().get(bookingIndex - 1);
                                client.cancelBooking(bookingToCancel.getOffering());
                                System.out.println("Booking canceled successfully!");
                            } else {
                                System.out.println("Error: Invalid booking number. Please try again.");
                            }
                        } else {
                            System.out.println("Error: Please enter a valid number.");
                            scanner.nextLine();
                        }
                    } else {
                        System.out.println("No bookings available to cancel.");
                    }
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

    private <T> void listItems(List<T> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i).toString());
        }
    }

    private <T> T selectFromItems(List<T> options) {
        listItems(options);
        System.out.println("Please enter the number of your selection:");
        int selectedNumber = 0;
        while (true) {
            selectedNumber = Integer.parseInt(prompt());
            if (selectedNumber >= 1 && selectedNumber <= options.size())
                break;
            System.out.println("Invalid selection. Please select a number between 1 and " + options.size() + ".");
        }
        return options.get(selectedNumber - 1);
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
        this.locationMapper.close();
        this.offeringMapper.close();
        this.organizationMapper.close();
        this.spaceMapper.close();
        this.userMapper.close();
    }

    private void setUser(User user) {
        this.user = user;
    }

}
