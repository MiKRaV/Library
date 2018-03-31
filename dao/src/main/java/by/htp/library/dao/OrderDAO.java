package by.htp.library.dao;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;

import java.util.List;

public interface OrderDAO {
    List<Order> getAllOrders() throws DAOException;
    List<Order> getUsersOrders(int userID, int pageNumber, int pageSize) throws DAOException;
    List<Order> getUsersOrders(User user) throws DAOException;
}
