package soen342;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

import soen342.database.*;

import soen342.users.*;
import soen342.location.*;
import soen342.reservation.*;

public class Populate {
    public static void main(String[] args) {
        deleteDatabaseFile();
        populateDatabaseFile();
    }

    public static void deleteDatabaseFile() {
        String dbFilePath = "soen342db.mv.db";
        java.io.File dbFile = new java.io.File(dbFilePath);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    public static void populateDatabaseFile() {
        JPAUtil.init();
        OrganizationMapper organizationMapper = new OrganizationMapper();
        LessonMapper lessonMapper = new LessonMapper();
        OfferingMapper offeringMapper = new OfferingMapper();
        ProvinceMapper provinceMapper = new ProvinceMapper();
        CityMapper cityMapper = new CityMapper();
        UserMapper userMapper = new UserMapper();
        LocationMapper locationMapper = new LocationMapper();
        BookingMapper bookingMapper = new BookingMapper();

        Province quebec = new Province("Quebec");
        provinceMapper.create(quebec);

        City montreal = new City("Montreal", quebec);
        cityMapper.create(montreal);

        City quebecCity = new City("Quebec City", quebec);
        cityMapper.create(quebecCity);

        Organization organization = new Organization("Dunder Mifflin Paper Company");
        Lesson swimmingLesson = new Lesson("Swimming");
        Lesson judoLesson = new Lesson("Judo");
        organization.addLesson(swimmingLesson);
        organization.addLesson(judoLesson);
        organizationMapper.create(organization);

        Location EVBuilding = new Location("EV-Building", montreal, "123 Happy St");
        Space leGym = new Space("Le Gym", 100);
        EVBuilding.addSpace(leGym);
        locationMapper.create(EVBuilding);
        organization.addOwnedSpace(leGym);
        organizationMapper.update(organization);

        Location ULaval = new Location("ULaval", quebecCity, "123 Also Happy St");
        Space ulavalGym = new Space("ULaval Gym", 200);
        ULaval.addSpace(ulavalGym);
        locationMapper.create(ULaval);
        organization.addOwnedSpace(ulavalGym);
        organizationMapper.update(organization);

        // START CREATE OFFERING PROCEDURE

        // 1. Create & persist TimeSlots
        TimeSlot timeslot1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot timeslot2 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(12, 30));
        leGym.addTimeSlot(timeslot1);
        ulavalGym.addTimeSlot(timeslot2);
        locationMapper.update(EVBuilding);
        locationMapper.update(ULaval);

        // 2. Retrieve 'hibernate-managed' timeslot instances
        timeslot1 = EVBuilding.getSpaces().get(0).getLastTimeSlot();
        timeslot2 = ULaval.getSpaces().get(0).getLastTimeSlot();

        // 3. Create offerings using managed timeslot instances
        Offering o1 = swimmingLesson.addOffering(10, leGym, timeslot1);
        Offering o2 = judoLesson.addOffering(20, ulavalGym, timeslot2);

        // 4. Persist the offerings
        offeringMapper.create(o1);
        offeringMapper.create(o2);

        // END CREATE OFFERING PROCEDURE

        Instructor i = new Instructor("pam@concordia.ca", "password", organization, Arrays.asList(montreal),
                "Swimming");
        userMapper.create(Admin.getInstance("dwight@concordia.ca", "password", organization));
        userMapper.create(i);
        userMapper.create(new Instructor("jim@concordia.ca", "password", organization,
                Arrays.asList(montreal, quebecCity), "Judo"));
        userMapper.create(new Client("michael@concordia.ca", "password", organization, 50));
        
        Client angela = new Client("angela@concordia.ca", "password", organization, 40);
        userMapper.create(angela);

        Client underageClient = new Client("child", organization, 11, angela);
        userMapper.create(underageClient);

        i.selectOffering(swimmingLesson.getOfferings().get(0));
        offeringMapper.update(swimmingLesson.getOfferings().get(0));

        Booking booking1 = new Booking(angela, o1);
        bookingMapper.create(booking1);

        Booking booking2 = new Booking(underageClient, o1);
        bookingMapper.create(booking2);

        organizationMapper.close();
        lessonMapper.close();
        offeringMapper.close();
        provinceMapper.close();
        cityMapper.close();
        userMapper.close();
        locationMapper.close();
        bookingMapper.close();
        JPAUtil.closeEntityManagerFactory();
    }

}