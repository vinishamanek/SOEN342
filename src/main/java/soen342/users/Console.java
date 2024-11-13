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
import soen342.reservation.TimeSlotOverlapException;

public class Console {

    private User user;
    private Scanner scanner;
    private BookingMapper bookingMapper;
    private LocationMapper locationMapper;
    private OfferingMapper offeringMapper;
    private OrganizationMapper organizationMapper;
    private SpaceMapper spaceMapper;
    private UserMapper userMapper;

    public Console(User user) {
        this.setUser(user);
        this.scanner = new Scanner(System.in);
        this.bookingMapper = new BookingMapper();
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
            System.out.println("\nMain Menu:");
            System.out.println("v: View offerings");
            System.out.println("l: Login");
            System.out.println("s: Sign up");
            System.out.println("e: Exit");
            System.out.print("Select an option: ");
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
        System.out.println("\nSign Up Process");
        System.out.println("Select your role:");
        System.out.println("1. Client");
        System.out.println("2. Instructor");
        System.out.print("Enter your choice (1 or 2): ");

        int roleChoice = Integer.parseInt(prompt());
        Organization organization = this.organizationMapper.getDefault();

        System.out.println("Enter email: ");
        String email = prompt();

        System.out.println("Enter password: ");
        String password = prompt();

        if (roleChoice == 1) {
            System.out.println("Enter age: ");
            int age = Integer.parseInt(prompt());

            Client newClient = new Client(email, password, organization, age);

            userMapper.create(newClient);
            System.out.println("Client account created successfully for " + newClient.getEmail() + "!");

        } else if (roleChoice == 2) {
            System.out.println("Enter specialization: ");
            String specialization = prompt();

            Set<City> allCitiesSet = new HashSet<>();
            for (Space space : organization.getOwnedSpaces()) {
                Location location = space.getLocation();
                if (location != null && location.getCity() != null) {
                    allCitiesSet.add(location.getCity());
                }
            }
            List<City> allCities = new ArrayList<>(allCitiesSet);

            System.out.println("Available cities:");
            for (int i = 0; i < allCities.size(); i++) {
                System.out.println((i + 1) + ". " + allCities.get(i).getName());
            }

            System.out.println("Enter city numbers (comma-separated) where you're available to teach: ");
            String[] cityChoices = prompt().split(",");
            List<City> selectedCities = new ArrayList<>();

            for (String choice : cityChoices) {
                try {
                    int index = Integer.parseInt(choice.trim()) - 1;
                    if (index >= 0 && index < allCities.size()) {
                        selectedCities.add(allCities.get(index));
                    } else {
                        System.out.println("City number " + (index + 1) + " is out of range.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + choice.trim());
                }
            }

            Instructor newInstructor = new Instructor(email, password, organization, selectedCities, specialization);
            userMapper.create(newInstructor);
            System.out.println("Instructor account created successfully " + newInstructor.getEmail() + "!");
        }
    }

    private void login() {
        while (true) {
            System.out.print("\nEnter email: ");
            String email = prompt();
            System.out.print("Enter password: ");
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
            System.out.println("\nAdmin Menu:");
            System.out.println("o: Create offering");
            System.out.println("l: Create lesson");
            System.out.println("v: View bookings");
            System.out.println("c: Cancel booking");
            System.out.println("a: View accounts");
            System.out.println("d: Delete account");
            System.out.println("e: Logout");
            System.out.print("Select an option: ");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'o':
                    System.out.println("\nCreating New Offering");
                    System.out.println("Available lessons: ");
                    List<Lesson> lessons = this.user.getOrganization().getLessons();
                    Lesson selectedLesson = selectFromItems(lessons);

                    System.out.println("Enter capacity: ");
                    int capacity = Integer.parseInt(prompt());

                    List<Space> spaces = this.user.getOrganization().getOwnedSpaces();
                    System.out.println("Available spaces:");
                    Space selectedSpace = selectFromItems(spaces);

                    TimeSlot timeSlot = promptTimeSlot(selectedSpace);
                    this.locationMapper.update(selectedSpace.getLocation());
                    timeSlot = selectedSpace.getLastTimeSlot();
                    Offering offering = selectedLesson.addOffering(capacity, selectedSpace, timeSlot);
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
                case 'v':
                    System.out.println("\nAll Bookings:");
                    List<Booking> bookings = this.bookingMapper.findAll();
                    this.listItems(bookings);
                    break;
                case 'c':
                    List<Booking> allBookings = this.bookingMapper.findAll();
                    if (allBookings.isEmpty()) {
                        System.out.println("There are no bookings to cancel.");
                    } else {
                        System.out.println("Select the booking you'd like to cancel:");
                        Booking selectedBooking = this.selectFromItems(allBookings);
                        this.bookingMapper.delete(selectedBooking);
                        System.out.println("Booking canceled successfully.");
                    }
                    break;
                case 'a':
                    System.out.println("\nAll User Accounts:");
                    List<User> users = this.userMapper.findAllNonAdmins();
                    this.listItems(users);
                    break;
                case 'd':
                    List<User> allUsers = this.userMapper.findAllNonAdmins();
                    if (allUsers.isEmpty()) {
                        System.out.println("There are no accounts to delete.");
                    } else {
                        System.out.println("Select the account you'd like to delete:");
                        User selectedUser = this.selectFromItems(allUsers);
                        this.userMapper.delete(selectedUser);
                        System.out.println("Account deleted successfully.");
                    }
                    break;
                case 'e':
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid operation. Please enter 'o', 'l', 'v', 'c', 'a', 'd', or 'e'.");
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
            System.out.println("\nInstructor Menu:");
            System.out.println("v: View available offerings");
            System.out.println("s: Select offering");
            System.out.println("e: Logout");
            System.out.print("Select an option: ");

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
                    Offering selectedOffering = selectFromItems(offerings);
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
        Organization organization;
        String bookingFor;
        while (true) {
            System.out.println("\nClient Menu:");
            System.out.println("a: Add underage client");
            System.out.println("o: View offerings");
            System.out.println("m: Make booking");
            System.out.println("v: View bookings");
            System.out.println("c: Cancel booking");
            System.out.println("e: Logout");
            System.out.print("Select an option: ");
            char operation = prompt().toLowerCase().charAt(0);

            switch (operation) {
                case 'a':
                    System.out.print("\nEnter email for underage client: ");
                    String underageEmail = prompt();
                    System.out.println("Enter age for underage client:");
                    int underageAge = Integer.parseInt(prompt());
                    Client underageClient = new Client(underageEmail, this.organizationMapper.getDefault(), underageAge,
                            client);
                    client.addUnderageClient(underageClient);
                    client = (Client) userMapper.update(client);
                    System.out
                            .println("Underage client " + underageClient + " added successfully for client " + client);
                    break;
                case 'o':
                    System.out.println(
                            "Do you want to view offerings for yourself or an underage client? (self/underage)");
                    bookingFor = prompt().toLowerCase(); // User input for self or underage client

                    List<Offering> availableOfferingsToDisplay = new ArrayList<>();

                    if (bookingFor.equals("self")) {
                        availableOfferingsToDisplay = client.getAvailableClientOfferings();
                        listOfferings(availableOfferingsToDisplay);
                    } else if (bookingFor.equals("underage")) {
                        List<Client> underageClients = client.getUnderageClients();
                        System.out.println("Please select an underage client from the list:");
                        Client underageClientSelected = selectFromItems(underageClients);
                        availableOfferingsToDisplay = underageClientSelected.getAvailableClientOfferings();
                        listOfferings(availableOfferingsToDisplay);
                    } else {
                        System.out.println("Invalid choice. Please select 'self' or 'underage'.");
                    }
                    break;

                case 'm':
                    System.out.println(
                            "Do you want to make a booking for yourself or an underage client? (self/underage)");
                    bookingFor = prompt().toLowerCase();
                    Client targetClient = client;
                    if (bookingFor.equals("underage")) {
                        List<Client> underageClients = client.getUnderageClients();
                        if (underageClients.isEmpty()) {
                            System.out.println("No underage clients found.");
                            break;
                        }

                        Client underageClientSelected = selectFromItems(underageClients);
                        targetClient = underageClientSelected;
                        availableOfferings = targetClient.getAvailableClientOfferings();
                        listOfferings(availableOfferings);

                        if (availableOfferings.isEmpty()) {
                            System.out.println("No available offerings to book.");
                            break;
                        }

                        System.out.println("Enter the number associated with the offering you'd like to book:");
                        int offeringIndex = Integer.parseInt(prompt()) - 1;

                        if (offeringIndex >= 0 && offeringIndex < availableOfferings.size()) {
                            Offering selectedOffering = availableOfferings.get(offeringIndex);
                            Booking underageBooking = new Booking(targetClient, selectedOffering);
                            bookingMapper.create(underageBooking);
                            System.out.println("Booking created successfully for " + targetClient.getEmail() + "!");
                            targetClient.addBooking(underageBooking);
                        } else {
                            System.out.println("Error: Invalid offering number.");
                        }
                    } else {
                        availableOfferings = client.getAvailableClientOfferings();
                        listOfferings(availableOfferings);

                        if (availableOfferings.isEmpty()) {
                            System.out.println("No available offerings to book.");
                            break;
                        }

                        System.out.println("Enter the number associated with the offering you'd like to book:");
                        int offeringIndex = Integer.parseInt(prompt()) - 1;

                        if (offeringIndex >= 0 && offeringIndex < availableOfferings.size()) {
                            Offering selectedOffering = availableOfferings.get(offeringIndex);

                            Booking booking = new Booking(client, selectedOffering);
                            bookingMapper.create(booking);

                            client.createBooking(selectedOffering);
                            client = (Client) userMapper.update(client);
                            bookingMapper.update(booking);
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
                    System.out.println(
                            "Do you want to cancel a booking for yourself or an underage client? (self/underage)");
                    String cancelFor = prompt().trim().toLowerCase();

                    if (cancelFor.equals("self")) {
                        if (client.getBookings() != null && !client.getBookings().isEmpty()) {
                            Booking deleteBooking = this.selectFromItems(client.getBookings());
                            client.cancelBooking(deleteBooking);
                            client = (Client) this.userMapper.update(client);
                        } else {
                            System.out.println("You have no bookings to cancel.");
                        }
                    } else if (cancelFor.equals("underage")) {
                        List<Client> underageClients = client.getUnderageClients();

                        if (underageClients != null && !underageClients.isEmpty()) {
                            System.out.println("Select an underage client:");
                            Client underageClientToCancel = this.selectFromItems(underageClients);
                            if (underageClientToCancel.getBookings() != null
                                    && !underageClientToCancel.getBookings().isEmpty()) {
                                System.out.println("Bookings for " + underageClientToCancel.getEmail() + ":");
                                Booking deleteBookingUnderage = this
                                        .selectFromItems(underageClientToCancel.getBookings());
                                underageClientToCancel.cancelBooking(deleteBookingUnderage);
                                this.userMapper.update(underageClientToCancel);
                            } else {
                                System.out.println(underageClientToCancel.getEmail() + " has no bookings to cancel.");
                            }
                        } else {
                            System.out.println("No underage clients found. Please add one first.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter 'self' or 'underage'.");
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
            System.out.println("\nAvailable Offerings:");
            int number = 1;
            for (Offering offering : offerings) {
                System.out.println("Offering Number: " + number++);
                System.out.println(offering);
                System.out.println("-----------------------------------");
            }
        }
    }

    public TimeSlot promptTimeSlot(Space space) {
        TimeSlot timeslot;
        while (true) {
            try {
                System.out.println("Enter day of the week (e.g., Monday): ");
                String dayInput = prompt().toUpperCase();
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayInput);

                System.out.println("Enter start time (e.g., 9:00 AM): ");
                String startTimeInput = prompt();
                LocalTime startTime = parseTime(startTimeInput);

                System.out.println("Enter end time (e.g., 11:00 AM): ");
                String endTimeInput = prompt();
                LocalTime endTime = parseTime(endTimeInput);

                timeslot = new TimeSlot(dayOfWeek, startTime, endTime);
                space.addTimeSlot(timeslot);
                return timeslot;
            } catch (TimeSlotOverlapException e) {
                System.out.println("This timeslot overlaps with another: " + e.getOverlappingTimeSlot());
                continue;
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
        return scanner.nextLine().trim();
    }

    public void cleanup() {
        scanner.close();
        this.bookingMapper.close();
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
