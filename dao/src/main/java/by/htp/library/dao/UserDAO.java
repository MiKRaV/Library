package by.htp.library.dao;

import by.htp.library.entity.User;
import by.htp.library.entity.UserParameters;
import by.htp.library.dao.exception.DAOException;

import java.util.ArrayList;

public interface UserDAO {

    User logination(String login, String password) throws DAOException;
    void registration(User user) throws DAOException;
    boolean isUserExist(String login) throws DAOException;
    void changeUserData(User user, UserParameters data, String dataValue) throws DAOException;
    ArrayList<User> getAllUsersList() throws DAOException;
    void removeUser(String login) throws DAOException;
    User findUserByLogin(String login) throws DAOException;
    void blockUnlockUser(String login) throws DAOException;
}
