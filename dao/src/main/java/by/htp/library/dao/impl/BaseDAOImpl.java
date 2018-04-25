package by.htp.library.dao.impl;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.BaseDAOHelper;
import by.htp.library.dao.util.EMUtil;
import lombok.Getter;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository
public class BaseDAOImpl<T> implements by.htp.library.dao.BaseDAO<T> {
    Class<T> clazz;

    @PersistenceContext
    @Getter
    private EntityManager em;

    private CriteriaBuilder cb;

    @Override
    public T add(T obj) throws DAOException {
        try {
            em.persist(obj);
        } catch (RollbackException e) {
            throw new DAOException(BaseDAOHelper.MESSAGE_ADD_ERROR);
        }
        return obj;
    }

    @Override
    public T find(Serializable id) throws DAOException {
        T expectedObject;
        try {
            expectedObject = em.find(clazz, id);
        } catch (RollbackException e) {
            throw new DAOException(BaseDAOHelper.MESSAGE_FIND_ERROR);
        }
        return expectedObject;
    }

    @Override
    public List<T> findAll(int pageNumber, int pageSize) {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(clazz);
        Root<T> root = criteria.from(clazz);
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
        try {
            em.merge(obj);
        } catch (RollbackException e) {
            throw new DAOException(BaseDAOHelper.MESSAGE_CHANGE_ERROR);
        }
        return obj;
    }

    @Override
    public void remove(T obj) throws DAOException {
        try {
            em.remove(em.contains(obj) ? obj : em.merge(obj));
        } catch (RollbackException e) {
            throw new DAOException(BaseDAOHelper.MESSAGE_REMOVE_ERROR);
        }
    }

    @Override
    public long getCount() throws DAOException {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        criteria.select(cb.count(criteria.from(clazz)));
        long count = em.createQuery(criteria).getSingleResult();
        em.close();
        return count;
    }
}
