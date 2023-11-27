package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.Getter;

@Getter
public class HibernateManager {
    private static HibernateManager controller;

    // Creamos las EntityManagerFactory para manejar las entidades y transacciones
    private EntityManagerFactory entityManagerFactory;
    private EntityManager manager;
    private EntityTransaction transaction;

    private HibernateManager() {
    }

    public static HibernateManager getInstance() {
        if (controller == null)
            controller = new HibernateManager();
        return controller;
    }

    public void open() {
        entityManagerFactory = Persistence.createEntityManagerFactory("unidad-persistencia");
        manager = entityManagerFactory.createEntityManager();
        transaction = manager.getTransaction();
    }

    public void close() {
        manager.close();
        entityManagerFactory.close();
    }

    public EntityManager openSession() {
        return entityManagerFactory.createEntityManager();
    }

    public void closeSession(EntityManager entityManager) {
        entityManager.close();
    }

    public void beginTransaction() {
        transaction.begin();
    }

    public void commitTransaction() {
        transaction.commit();
    }

    public void rollbackTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
