package by.htp.library.service.impl;

import by.htp.library.dao.*;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.BookStatus;
import by.htp.library.entity.helper.OrderStatus;
import by.htp.library.entity.helper.UserType;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import by.htp.library.service.helper.ServiceMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User createOrder(User user) throws ServiceException {
        Order order = new Order(null, LocalDateTime.now(), null, null, user, OrderStatus.IN_PROCESSING);
        try {
            List<Book> bookInOrder = new ArrayList<>();
            bookInOrder.addAll(user.getBasket());
            userService.clearBasket(user);
            order.setBooksInOrder(bookInOrder);
            user = userDAO.find(user.getId());
            order.setUser(user);
            order = orderDAO.add(order);
            for (Book book : bookInOrder) {
                book = bookDAO.find(book.getId());
                book.setOrder(order);
                book.setStatus(BookStatus.UNAVAILABLE);
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
        UserType userType = user.getType();
        try {
            switch (userType) {
                case ADMIN:
                    orders = orderDAO.findAll(pageNumber, pageSize);
                    break;
                case READER:
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
    public void changeOrderStatus(int orderID, OrderStatus newOrderStatus) throws ServiceException {
        Order order = getOrder(orderID);
        OrderStatus orderStatus = order.getStatus();

        switch (newOrderStatus) {
            case ACCEPTED_FOR_EXECUTION:
                orderStatus = OrderStatus.ACCEPTED_FOR_EXECUTION;
                break;
            case READY_FOR_ISSUE:
                orderStatus = OrderStatus.READY_FOR_ISSUE;
                break;
            case FULFILLED:
                orderStatus = OrderStatus.FULFILLED;
                break;
        }

        order.setStatus(orderStatus);
        order.setOrderUpdateTime(LocalDateTime.now());

        try {
            orderDAO.update(order);
            if (order.getStatus() == OrderStatus.FULFILLED)
                userService.addNoteToSubscriptionFromOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(ServiceMessages.ORDER_STATUS_IS_NOT_CHANGED, e);
        }
    }
}
