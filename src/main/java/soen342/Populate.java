package soen342;

import java.util.Arrays;

import soen342.database.*;

import soen342.users.*;
import soen342.location.*;
import soen342.reservation.Lesson;

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
        SpaceMapper spaceMapper = new SpaceMapper();

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

        userMapper.create(Admin.getInstance("dwight@concordia.ca", "password", organization));
        userMapper.create(new Instructor("pam@concordia.ca", "password", organization, Arrays.asList(montreal),
                "Swimming"));
        userMapper.create(new Instructor("jim@concordia.ca", "password", organization,
                Arrays.asList(montreal, quebecCity), "Judo"));
        userMapper.create(new Client("michael@concordia.ca", "password", organization));
        userMapper.create(new Client("angela@concordia.ca", "password", organization));


        Location EVBuilding = new Location("EV-Building", montreal, "123 Happy St");
        locationMapper.create(EVBuilding);

        Space ulavalGym = new Space("ULaval Gym", 200);
        ULaval.addSpace(ulavalGym);
        organization.addOwnedSpace(ulavalGym);
        spaceMapper.create(ulavalGym);

        organizationMapper.close();
        lessonMapper.close();
        offeringMapper.close();
        provinceMapper.close();
        cityMapper.close();
        userMapper.close();
        locationMapper.close();
        spaceMapper.close();
        JPAUtil.closeEntityManagerFactory();
    }

}