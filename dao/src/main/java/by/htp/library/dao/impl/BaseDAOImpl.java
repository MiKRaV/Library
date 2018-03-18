package by.htp.library.dao.impl;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.BaseDAOHelper;
import by.htp.library.dao.util.EMUtil;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseDAOImpl<T> implements by.htp.library.dao.BaseDAO<T> {
    private EntityManager em;
    private CriteriaBuilder cb;

    @Override
    public void add(T obj) throws DAOException {
        em = EMUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new DAOException();
        } finally {
            em.close();
        }
    }

    @Override
    public T find(Class<T> tClass, Object id) throws DAOException {
        em = EMUtil.getEntityManager();
        T expectedObject;
        try {
            em.getTransaction().begin();
            expectedObject = em.find(tClass, id);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new DAOException();
        } finally {
            em.close();
        }
        return expectedObject;
    }

    @Override
    public List<T> findAll(Class<T> tClass, int pageNumber, int pageSize) {
        em = EMUtil.getEntityManager();
        cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(tClass);
        Root<T> root = criteria.from(tClass);
        criteria.select(root);
        TypedQuery<T> typedQuery = em.createQuery(criteria);
        typedQuery.setFirstResult(pageSize * (pageNumber - 1));
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    @Override
    public void change(T obj) throws DAOException {
        em = EMUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new DAOException();
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(T obj) throws DAOException {
        em = EMUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(obj) ? obj : em.merge(obj));
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new DAOException();
        } finally {
            em.close();
        }
    }
}
