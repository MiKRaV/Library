package by.htp.library.dao.impl;

import by.htp.library.dao.OrderDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.BaseDAOHelper;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.OrderHelper;
import by.htp.library.entity.helper.UserHelper;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderDAOImpl extends BaseDAOImpl<Order> implements OrderDAO {

    @PersistenceContext
    @Getter
    private EntityManager em;
    private CriteriaBuilder cb;

    public OrderDAOImpl() {
        clazz = Order.class;
    }

    @Override
    public List<Order> getAllOrders() throws DAOException {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Order> getUsersOrders(int userID, int pageNumber, int pageSize) throws DAOException {
        cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(clazz);
        Root<Order> root = criteria.from(clazz);
        criteria.select(root).where(cb.equal(root.get(OrderHelper.USER).get(UserHelper.ID_USER), userID));
        TypedQuery<Order> typedQuery = em.createQuery(criteria);
        typedQuery.setFirstResult(pageSize * (pageNumber - 1));
        typedQuery.setMaxResults(pageSize);
        List<Order> orderList = typedQuery.getResultList();
        return orderList;
    }

    @Override
    public List<Order> getUsersOrders(User user) throws DAOException {
        List<Order> orderList;
        try {
            user = em.find(User.class, user.getId());
            orderList = user.getOrders();
        } catch (RollbackException e) {
            throw new DAOException(BaseDAOHelper.MESSAGE_ADD_ERROR);
        }
        return orderList;
    }
}
