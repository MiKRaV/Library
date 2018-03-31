package by.htp.library.dao.impl;

import by.htp.library.dao.OrderDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.BaseDAOHelper;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.OrderHelper;
import by.htp.library.entity.helper.UserHelper;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private EntityManager em = EMUtil.getEntityManager();
    private CriteriaBuilder cb = em.getCriteriaBuilder();

    @Override
    public List<Order> getAllOrders() throws DAOException {
        return null;
    }

    @Override
    public List<Order> getUsersOrders(int userID, int pageNumber, int pageSize) throws DAOException {
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.select(root).where(cb.equal(root.get(OrderHelper.USER).get(UserHelper.ID_USER), userID));
        TypedQuery<Order> typedQuery = em.createQuery(criteria);
        typedQuery.setFirstResult(pageSize * (pageNumber - 1));
        typedQuery.setMaxResults(pageSize);
        List<Order> orderList = typedQuery.getResultList();
        return orderList;
    }

    @Override
    public List<Order> getUsersOrders(User user) throws DAOException {
        List<Order> orderList = null;
        try {
            em.getTransaction().begin();
            user = em.find(User.class, user.getId());
            orderList = user.getOrders();
            //em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
            throw new DAOException(BaseDAOHelper.MESSAGE_ADD_ERROR);
        } finally {
            em.close();
        }
        return orderList;
    }
}
