package isi.diti4.bibliodakar.dao;

import isi.diti4.bibliodakar.entity.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class BaseRepository<T extends Document> {

    protected final EntityManager em = JpaUtil.getEntityManager();
    private final Class<T> type;

    protected BaseRepository(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        return em.createQuery(
                        "SELECT e FROM " + type.getSimpleName() + " e ORDER BY e.code",
                        type)
                .getResultList();
    }

    public T findByCode(String code) {
        return em.find(type, code);
    }

    public void save(T entity) {
        executeInTransaction(() -> em.persist(entity));
    }

    public void updateEmprunt(String code, boolean emprunte) {
        executeInTransaction(() -> {
            T entity = em.find(type, code);
            if (entity != null) {
                if (emprunte) {
                    entity.emprunter();
                } else {
                    entity.rendre();
                }
            }
        });
    }

    public void delete(String code) {
        executeInTransaction(() -> {
            T entity = em.find(type, code);
            if (entity != null) {
                em.remove(entity);
            }
        });
    }

    public long count() {
        return em.createQuery(
                        "SELECT COUNT(e) FROM " + type.getSimpleName() + " e",
                        Long.class)
                .getSingleResult();
    }

    protected void executeInTransaction(Runnable action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.run();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}
