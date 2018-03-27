package by.htp.library.dao.impl;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.BaseDAOHelper;
import by.htp.library.dao.util.EMUtil;
import org.hibernate.Criteria;

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
            throw new DAOException(BaseDAOHelper.MESSAGE_ADD_ERROR);
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
            throw new DAOException(BaseDAOHelper.MESSAGE_FIND_ERROR);
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
        List<T> tList = typedQuery.getResultList();
        em.close();

        return tList;
    }

    @Override
    public T update(T obj) throws DAOException {
        em = EMUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new DAOException(BaseDAOHelper.MESSAGE_CHANGE_ERROR);
        } finally {
            em.close();
        }
        return obj;
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
            throw new DAOException(BaseDAOHelper.MESSAGE_REMOVE_ERROR);
        } finally {
            em.close();
        }
    }

    @Override
    public long getCount(Class<T> tClass) throws DAOException {
        em = EMUtil.getEntityManager();
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        criteria.select(cb.count(criteria.from(tClass)));
        long count = em.createQuery(criteria).getSingleResult();
        em.close();
        return count;
    }
}
