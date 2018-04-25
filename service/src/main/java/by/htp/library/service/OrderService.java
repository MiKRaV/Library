package by.htp.library.service;

import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.OrderStatus;

import java.util.List;

public interface OrderService {
    User createOrder(User user) throws ServiceException;
    List<Order> getAllOrders(User user, int pageNumber, int pageSize) throws ServiceException;
    List<Order> getUsersOrders(int userID, int pageNumber, int pageSize) throws ServiceException;
    long getTotalOrderCount() throws ServiceException;
    Order getOrder(int orderID) throws ServiceException;
    void changeOrderStatus(int orderID, OrderStatus newOrderStatus) throws ServiceException;
}
