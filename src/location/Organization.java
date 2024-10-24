package location;

import java.util.ArrayList;
import java.util.List;

import reservation.Lesson;
import reservation.Offering;
import reservation.TimeSlot;
import users.Instructor;
import users.Client;

public class Organization {

    public String name;

    public List<Space> ownedSpaces = new ArrayList<Space>();
    public List<Space> rentedSpaces = new ArrayList<Space>();
    public List<Lesson> Lessons = new ArrayList<Lesson>();

    public Organization(String name) {
        this.name = name;
    }

    public void createOffering(Lesson lesson, int capacity, Space space, TimeSlot timeslot) {
        space.addTimeSlot(timeslot);
        lesson.addOffering(capacity, space, timeslot);
    }

    public List<Offering> getAvailableInstructorOfferings(Instructor instructor) {
        List<Offering> offerings = new ArrayList<Offering>();
        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                if (instructor.getAvailableCities().contains(offering.getLocation().getCity())) {
                    offerings.add(offering);
                }
            }
        }
        return offerings;
    }

    public List<Offering> getAvailableClientOfferings(Client client) {
        List<Offering> offerings = new ArrayList<Offering>();
        for (Lesson lesson : Lessons) {
            for (Offering offering : lesson.getOfferings()) {
                if (offering.hasInstructor()) {
                    offerings.add(offering);
                }
            }
        }
        return offerings;
    }

    public void addLesson(Lesson lesson) {
        Lessons.add(lesson);
    }

    public void addOwnedSpace(Space space) {
        ownedSpaces.add(space);
    }

    public List<Space> getOwnedSpaces() {
        return ownedSpaces;
    }

}
