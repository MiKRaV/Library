package by.htp.library.service.impl;

import by.htp.library.dao.*;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.BookStatus;
import by.htp.library.entity.helper.OrderStatus;
import by.htp.library.entity.helper.UserHelper;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import by.htp.library.service.helper.ServiceMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.FetchType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public User createOrder(Order order) throws ServiceException {
        int userID = order.getUser().getId();
        User user = null;
        try {
            user = userDAO.find(userID);
            List<Book> basket = user.getBasket();
            List<Book> bookInOrder = new ArrayList<>();
            bookInOrder.addAll(basket);
            user = userService.clearBasket(user);
            order.setBooksInOrder(bookInOrder);
            orderDAO.add(order);
            for (Book book : basket) {
                book.setOrder(order);
                bookDAO.update(book);
            }
            user.getOrders().add(order);
            user = userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_NOT_CREATED, e);
        }
        return user;
    }

    @Override
    @Transactional
    public List<Order> getAllOrders(User user, int pageNumber, int pageSize) throws ServiceException {
        List<Order> orders = null;
        String userType = user.getType();
        try {
            switch (userType) {
                case UserHelper.TYPE_ADMIN:
                    orders = orderDAO.findAll(pageNumber, pageSize);
                    break;
                case UserHelper.TYPE_READER:
                    //orders = user.getOrders();
                    orders = orderDAO.getUsersOrders(user.getId(), pageNumber, pageSize);
                    break;
            }

        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_LIST_NOT_RECEIVED, e);
        }
        return orders;
    }

    @Override
    @Transactional
    public List<Order> getUsersOrders(int userID, int pageNumber, int pageSize) throws ServiceException {
        List<Order> orders = null;
        try {
            orders = orderDAO.getUsersOrders(userID, pageNumber, pageSize);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    @Transactional
    public long getTotalOrderCount() throws ServiceException {
        long orderCount;
        try {
            orderCount = orderDAO.getCount();
        } catch (DAOException e) {
            throw new ServiceException();
        }
        return orderCount;
    }

    @Override
    @Transactional
    public Order getOrder(int orderID) throws ServiceException {
        Order order;
        try {
            order = orderDAO.find(orderID);
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_NOT_RECEIVED, e);
        }

        return order;
    }

    @Override
    @Transactional
    public void changeOrderStatus(int orderID, String newOrderStatus) throws ServiceException {
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
            orderDAO.update(order);
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_STATUS_IS_NOT_CHANGED, e);
        }
    }
}
