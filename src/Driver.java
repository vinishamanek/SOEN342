import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import location.City;
import location.Location;
import location.Organization;
import location.Province;
import location.Space;
import reservation.Lesson;
import reservation.TimeSlot;
import users.Admin;
import users.Client;
import users.Console;
import users.Instructor;
import users.User;

public class Driver {
    public static void main(String[] args) {
        Organization organization = Organization.getInstance("Dunder Mifflin Paper Company");

        Province quebec = new Province("Quebec");
        City montreal = new City("Montreal", quebec);
        City quebecCity = new City("Quebec City", quebec);

        Lesson swimmingLesson = new Lesson("Swimming");
        Lesson judoLesson = new Lesson("Judo");
        organization.addLesson(swimmingLesson);
        organization.addLesson(judoLesson);

        Location EVBuilding = new Location("EV-Building", montreal, "123 Happy St");
        Space leGym = new Space("Le Gym", 100);
        EVBuilding.addSpace(leGym);
        organization.addOwnedSpace(leGym);

        Location ULaval = new Location("ULaval", quebecCity, "123 Also Happy St");
        Space ulavalGym = new Space("ULaval Gym", 200);
        ULaval.addSpace(ulavalGym);
        organization.addOwnedSpace(ulavalGym);

        TimeSlot timeslot1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot timeslot2 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(12, 30));

        organization.createOffering(swimmingLesson, 10, leGym, timeslot1);
        organization.createOffering(judoLesson, 20, ulavalGym, timeslot2);

        User[] users = new User[5];
        users[0] = Admin.getInstance("dwight@concordia.ca", "password", organization);
        users[1] = new Instructor("pam@concordia.ca", "password", organization, Arrays.asList(montreal),
                "Swimming");
        users[2] = new Instructor("jim@concordia.ca", "password", organization,
                Arrays.asList(montreal, quebecCity), "Judo");
        users[3] = new Client("michael@concordia.ca", "password", organization);
        users[4] = new Client("angela@concordia.ca", "password", organization);
        User.setUsers(users);

        ((Instructor) users[1]).selectOffering(swimmingLesson.getOfferings().get(0));

        Console console = new Console(null);
        console.run();
        console.cleanup();
    }
}
