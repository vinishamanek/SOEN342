package soen342.database;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import soen342.location.Organization;

public class OrganizationMapper extends AbstractMapper<Organization> {

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

}
