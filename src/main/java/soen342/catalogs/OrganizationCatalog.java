package soen342.catalogs;

import java.util.ArrayList;
import java.util.List;

import soen342.location.Organization;

public class OrganizationCatalog {
    private static OrganizationCatalog instance;
    private List<Organization> organizations;

    private OrganizationCatalog(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public static OrganizationCatalog getInstance() {
        if (instance == null) {
            instance = new OrganizationCatalog(new ArrayList<Organization>());
        }
        return instance;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }
}
