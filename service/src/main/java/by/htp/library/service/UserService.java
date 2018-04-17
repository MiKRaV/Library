package by.htp.library.service;

import java.util.List;

import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.UserParameters;
import org.springframework.stereotype.Service;

public interface UserService {
	
	User logination(String login, String password) throws ServiceException;
	void registration(User user) throws ServiceException;
	boolean isUserExist(String login) throws ServiceException;
	User updateUser(User user) throws ServiceException;
	User updateUser(User user, UserParameters data, String dataValue) throws ServiceException;
	List<User> getAllUsersList(int pageNumber, int pageSize) throws ServiceException;
	void removeUser(String login) throws ServiceException;
	User findUserByLogin(String login) throws ServiceException;
	void blockUnlockUser(String login) throws ServiceException;
	long getUserCount() throws ServiceException;
	User addBookToBasket(User user, int bookID) throws ServiceException;
	User removeBookFromBasket(User user, int bookID) throws ServiceException;
	User clearBasket(User user) throws ServiceException;
}
