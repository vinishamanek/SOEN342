package soen342;

import java.util.Arrays;

import soen342.database.JPAUtil;
import soen342.database.UserMapper;

import soen342.users.*;
import soen342.location.*;
import soen342.location.Organization;

public class Populate {
    public static void main(String[] args) {
        JPAUtil.init();
        UserMapper.init();

        Province quebec = new Province("Quebec");
        City montreal = new City("Montreal", quebec);
        City quebecCity = new City("Quebec City", quebec);

        Organization organization = Organization.getInstance("Dunder Mifflin Paper Company");
        UserMapper.create(Admin.getInstance("dwight@concordia.ca", "password", organization));
        UserMapper.create(new Instructor("pam@concordia.ca", "password", organization, Arrays.asList(montreal),
                "Swimming"));
        UserMapper.create(new Instructor("jim@concordia.ca", "password", organization,
                Arrays.asList(montreal, quebecCity), "Judo"));
        UserMapper.create(new Client("michael@concordia.ca", "password", organization));
        UserMapper.create(new Client("angela@concordia.ca", "password", organization));

        UserMapper.close();
        JPAUtil.closeEntityManagerFactory();
    }

}