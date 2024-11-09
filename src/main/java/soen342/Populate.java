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

        Organization organization = Organization.getInstance("Dunder Mifflin Paper Company");
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

        TimeSlot timeslot1 = new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
        TimeSlot timeslot2 = new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(12, 30));

        Offering o1 = organization.createOffering(swimmingLesson, 10, leGym, timeslot1);
        Offering o2 = organization.createOffering(judoLesson, 20, ulavalGym, timeslot2);

        // persist the timeslots first so that the offerings can reference them
        locationMapper.update(EVBuilding);
        locationMapper.update(ULaval);
        offeringMapper.create(o1);
        offeringMapper.create(o2);

        userMapper.create(Admin.getInstance("dwight@concordia.ca", "password", organization));
        userMapper.create(new Instructor("pam@concordia.ca", "password", organization, Arrays.asList(montreal),
                "Swimming"));
        userMapper.create(new Instructor("jim@concordia.ca", "password", organization,
                Arrays.asList(montreal, quebecCity), "Judo"));
        userMapper.create(new Client("michael@concordia.ca", "password", organization));
        userMapper.create(new Client("angela@concordia.ca", "password", organization));

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