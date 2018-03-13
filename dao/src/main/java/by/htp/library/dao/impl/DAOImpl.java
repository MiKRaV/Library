package by.htp.library.dao.impl;

import by.htp.library.dao.DAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.EMUtil;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

public class DAOImpl<T> implements DAO<T> {
    private EntityManager em;

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
