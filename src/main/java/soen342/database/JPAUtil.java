package soen342.database;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static void init() {
        try {
            Logger hibernateLogger = Logger.getLogger("org.hibernate");
            hibernateLogger.setLevel(Level.SEVERE);
            entityManagerFactory = Persistence.createEntityManagerFactory("soen342PU");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("Entity Manager Factory has not been initialized.");
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}