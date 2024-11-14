package soen342.database;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import soen342.catalogs.OrganizationCatalog;
import soen342.location.Organization;

public class OrganizationMapper extends AbstractMapper<Organization> {

    private static OrganizationCatalog organizationCatalog;

    public OrganizationMapper() {
        super();
        organizationCatalog = OrganizationCatalog.getInstance();
    }

    public List<Organization> findAll() {
        TypedQuery<Organization> query = entityManager.createQuery(
                "SELECT o FROM Organization o ORDER BY o.id", Organization.class);
        organizationCatalog.setOrganizations(query.getResultList());
        return organizationCatalog.getOrganizations();
    }

    public Organization findByName(String name) {
        try {
            TypedQuery<Organization> query = entityManager.createQuery(
                    "SELECT o FROM Organization o WHERE o.name = :name", Organization.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Organization getDefault() {
        try {
            TypedQuery<Organization> query = entityManager.createQuery(
                    "SELECT o FROM Organization o ORDER BY o.id", Organization.class);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
