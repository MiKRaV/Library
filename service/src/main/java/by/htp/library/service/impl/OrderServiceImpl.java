package by.htp.library.service.impl;

import by.htp.library.dao.BaseDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.OrderDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.OrderStatus;
import by.htp.library.entity.helper.UserHelper;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.helper.ServiceMessages;

import javax.persistence.FetchType;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    @Override
    public void createOrder(Order order) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        BaseDAO baseDAO = daoFactory.getBaseDAO();
        List<Book> booksInOrder = order.getBooksInOrder();
        try {
            baseDAO.add(order);
            for (Book book : booksInOrder) {
                book.setOrder(order);
                baseDAO.update(book);
            }
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_NOT_CREATED, e);
        }
    }

    @Override
    public List<Order> getAllOrders(User user, int pageNumber, int pageSize) throws ServiceException {
        List<Order> orders = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        BaseDAO baseDAO = daoFactory.getBaseDAO();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        String userType = user.getType();
        try {
            switch (userType) {
                case UserHelper.TYPE_ADMIN:
                    orders = baseDAO.findAll(Order.class, pageNumber, pageSize);
                    break;
                case UserHelper.TYPE_READER:
                    //orders = user.getOrders();
                    orders = orderDAO.getUsersOrders(user);
                    break;
            }

        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_LIST_NOT_RECEIVED, e);
        }
        return orders;
    }

    @Override
    public List<Order> getUsersOrders(int userID, int pageNumber, int pageSize) throws ServiceException {
        List<Order> orders = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();

        try {
            orders = orderDAO.getUsersOrders(userID, pageNumber, pageSize);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long getTotalOrderCount() throws ServiceException {
        long orderCount;
        DAOFactory daoFactory;
        try {
            daoFactory = DAOFactory.getInstance();
            BaseDAO baseDAO = daoFactory.getBaseDAO();
            orderCount = baseDAO.getCount(Order.class);
        } catch (DAOException e) {
            throw new ServiceException();
        }
        return orderCount;
    }

    @Override
    public Order getOrder(int orderID) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        BaseDAO baseDAO = daoFactory.getBaseDAO();
        Order order = null;
        try {
            order = (Order) baseDAO.find(Order.class, orderID);
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_NOT_RECEIVED, e);
        }

        return order;
    }

    @Override
    public void changeOrderStatus(int orderID, String newOrderStatus) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        BaseDAO baseDAO = daoFactory.getBaseDAO();

        Order order = getOrder(orderID);
        String orderStatus = order.getStatus();

        switch (newOrderStatus) {
            case OrderStatus.ACCEPTED_FOR_EXECUTION:
                orderStatus = OrderStatus.ACCEPTED_FOR_EXECUTION;
                break;
            case OrderStatus.AWAITS_DELIVERY:
                orderStatus = OrderStatus.AWAITS_DELIVERY;
                break;
            case OrderStatus.FULFILLED:
                orderStatus = OrderStatus.FULFILLED;
                break;
        }

        order.setStatus(orderStatus);
        order.setOrderUpdateTime(LocalDateTime.now());

        try {
            baseDAO.update(order);
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_STATUS_IS_NOT_CHANGED, e);
        }
    }
}
