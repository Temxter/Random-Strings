package dao;

import creators.EntityManagerFactorySingleton;
import model.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class EntityDao {

    public void create(Entity entity) {
        if (entity == null) {
            return;
        }
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            entityManager.close();
        } finally {
            entityManager.close();
        }
    }

    public Entity read(int id) {
        Entity entity = null;
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        try {
            entity = entityManager.find(Entity.class, id);
        } finally {
            entityManager.close();
        }
        return entity;
    }

    public Long getSumIntegers() {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        Query query = entityManager.createQuery("SELECT SUM(recordInteger) FROM Entity");
        Long queryResult = null;
        try {
            queryResult = (Long)query.getSingleResult();
        } finally {
            entityManager.close();
        }
        return queryResult;
    }

    public Double getMedianDoubles() {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        Query query = entityManager.createNativeQuery("SELECT percentile_cont(0.5) " +
                "WITHIN GROUP (ORDER BY recordDouble) FROM Entity");
        Double queryResult = null;
        try {
            queryResult = (Double)query.getSingleResult();
        } finally {
            entityManager.close();
        }
        return queryResult;
    }
}
