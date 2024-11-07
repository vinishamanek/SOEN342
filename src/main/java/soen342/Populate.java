package soen342;

import java.util.Arrays;

import soen342.database.*;

import soen342.users.*;
import soen342.location.*;
import soen342.location.Organization;

public class Populate {
    public static void main(String[] args) {
        JPAUtil.init();
        OrganizationMapper organizationMapper = new OrganizationMapper();
        ProvinceMapper provinceMapper = new ProvinceMapper();
        UserMapper userMapper = new UserMapper();

        Province quebec = new Province("Quebec");
        provinceMapper.create(quebec);

        City montreal = new City("Montreal", quebec);
        City quebecCity = new City("Quebec City", quebec);

        Organization organization = Organization.getInstance("Dunder Mifflin Paper Company");
        organizationMapper.create(organization);

        userMapper.create(Admin.getInstance("dwight@concordia.ca", "password", organization));
        userMapper.create(new Instructor("pam@concordia.ca", "password", organization, Arrays.asList(montreal),
                "Swimming"));
        userMapper.create(new Instructor("jim@concordia.ca", "password", organization,
                Arrays.asList(montreal, quebecCity), "Judo"));
        userMapper.create(new Client("michael@concordia.ca", "password", organization));
        userMapper.create(new Client("angela@concordia.ca", "password", organization));

        organizationMapper.close();
        provinceMapper.close();
        userMapper.close();
        JPAUtil.closeEntityManagerFactory();
    }

}