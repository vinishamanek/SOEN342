package soen342.catalogs;

import java.util.List;

import soen342.location.Organization;

public class OrganizationCatalog {
    private List<Organization> organizations;

    public OrganizationCatalog(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
