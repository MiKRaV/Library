package by.htp.library.dao;

import by.htp.library.entity.User;
import by.htp.library.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User logination(String login, String password) throws DAOException;
    void registration(User user) throws DAOException;
    boolean isUserExist(String login) throws DAOException;
    boolean isUserRemoved(String login) throws DAOException;
    User updateUser(User user) throws DAOException;
    List<User> getAllUsersList(int pageNumber, int pageSize) throws DAOException;
    void removeUser(String login) throws DAOException;
    User findUserByLogin(String login) throws DAOException;
    void blockUnlockUser(String login) throws DAOException;
    long getUserCount() throws DAOException;
}
