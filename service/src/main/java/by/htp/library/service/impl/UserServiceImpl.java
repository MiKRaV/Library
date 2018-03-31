package by.htp.library.service.impl;

import java.util.List;

import by.htp.library.dao.BaseDAO;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import by.htp.library.entity.helper.UserParameters;
import by.htp.library.service.Validator;
import by.htp.library.service.helper.ServiceMessages;

public class UserServiceImpl implements UserService {
	
	private Validator validator = new Validator();

	//LOGINATION
	@Override
	public User logination(String login, String password) throws ServiceException {
		// validation
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException(ServiceMessages.INCORRECT_LOGIN);
		} else if(!validator.validate(password, UserParameters.PASSWORD)) {
			throw new ServiceException(ServiceMessages.INCORRECT_PASSWORD);
		}

		User user = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.logination(login, password);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.LOGINATION_FAILED + " : " + e.getMessage(), e);
		}
		return user;
	}

	//REGISTRATION
	@Override
	public void registration(User user) throws ServiceException {
		// validation
		if(!validator.validate(user.getLogin(), UserParameters.LOGIN)) {
			throw new ServiceException(ServiceMessages.INCORRECT_LOGIN);
		} else if(!validator.validate(user.getPassword(), UserParameters.PASSWORD)) {
			throw new ServiceException(ServiceMessages.INCORRECT_PASSWORD);
		} else if(!validator.validate(user.getUserData().getName(), UserParameters.NAME)) {
			throw new ServiceException(ServiceMessages.INCORRECT_NAME);
		} else if(!validator.validate(user.getUserData().getSurname(), UserParameters.SURNAME)) {
			throw new ServiceException(ServiceMessages.INCORRECT_SURNAME);
		} else if(!validator.validate(user.getUserData().getEmail(), UserParameters.EMAIL)) {
			throw new ServiceException(ServiceMessages.INCORRECT_EMAIL);
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.registration(user);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.REGISTRATION_FAILED + " : " + e.getMessage(), e);
		}

	}

	//GETTING A LIST OF ALL USERS
	@Override
	public List<User> getAllUsersList(int pageNumber, int pageSize) throws ServiceException {
		List<User> users = null;
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();
		try {
			users = userDAO.getAllUsersList(pageNumber, pageSize);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.USERS_LIST_NOT_RECEIVED);
		}
		return users;
	}

	//CHECK IF THE USER EXISTS
	@Override
	public boolean isUserExist(String login) throws ServiceException {
		// validation
		if(!validator.validate(login, UserParameters.LOGIN))
			throw new ServiceException(ServiceMessages.INCORRECT_LOGIN);
		
		boolean isUserExist = false;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			isUserExist = userDAO.isUserExist(login);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.EXIST_USER_ERROR, e);
		}
		
		return isUserExist;
	}

	@Override
	public User updateUser(User user) throws ServiceException {
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.updateUser(user);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.UPDATE_USER_ERROR, e);
		}
		return user;
	}

	//UPDATE USER DATA
	@Override
	public User updateUser(User user, UserParameters data, String dataValue) throws ServiceException {
		// validation	
		if (!validator.validate(dataValue, data)) {
			throw new ServiceException(ServiceMessages.INCORRECT_VALUE);
		}

		switch (data) {
			case PASSWORD:
				user.setPassword(dataValue);
				break;
			case NAME:
				user.getUserData().setName(dataValue);
				break;
			case SURNAME:
				user.getUserData().setSurname(dataValue);
				break;
			case EMAIL:
				user.getUserData().setEmail(dataValue);
				break;
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.updateUser(user);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.UPDATE_USER_ERROR, e);
		}

		return user;
	}

	//REMOVING USER
	@Override
	public void removeUser(String login) throws ServiceException {
		//validation
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException(ServiceMessages.INCORRECT_LOGIN);
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.removeUser(login);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.REMOVING_FAILED, e);
		}
	}

	//SEARCH FOR A USER BY NAME
	@Override
	public User findUserByLogin(String login) throws ServiceException {
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException(ServiceMessages.INCORRECT_LOGIN);
		}
		
		User user = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.findUserByLogin(login);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.USER_SEARCH_FAILED, e);
		}
		return user;
	}

	//BLOCK/UNLOCK USER
	@Override
	public void blockUnlockUser(String login) throws ServiceException {
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException(ServiceMessages.INCORRECT_LOGIN);
		}
		
		DAOFactory daoFactory;
		try {
			daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.blockUnlockUser(login);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.BLOCK_UNLOCK_USER_FAILED + " : " + e.getMessage(), e);
		}
	}

	//GETTING THE COUNT OF USERS
	@Override
	public long getUserCount() throws ServiceException {
		long userCount;
		DAOFactory daoFactory;
		try {
			daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userCount = userDAO.getUserCount();
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return userCount;
	}

	//ADDING THE BOOK TO THE BASKET
	@Override
	public User addBookToBasket(User user, int bookID) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BaseDAO baseDAO = daoFactory.getBaseDAO();

		Book book;

		try {
			book = (Book) baseDAO.find(Book.class, bookID);
			for (Book bookFromBasket : user.getBasket()) {
				if (bookFromBasket.getId().equals(book.getId()))
					throw new ServiceException(ServiceMessages.BOOK_ALREADY_IN_BASKET);
			}
			user.getBasket().add(book);
			book.setUser(user);
			baseDAO.update(book);
			user = (User) baseDAO.update(user);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.BOOK_NOT_ADDED_TO_BASKET, e);
		}

		return user;
	}

    @Override
    public User removeBookFromBasket(User user, int bookID) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BaseDAO baseDAO = daoFactory.getBaseDAO();

		Book book;

		try {
			book = (Book) baseDAO.find(Book.class, bookID);
			List<Book> basket = user.getBasket();
			basket.remove(basket.indexOf(book));
			book.setUser(null);
			baseDAO.update(book);
			user = (User) baseDAO.update(user);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.BOOK_NOT_REMOVED_FROM_BASKET, e);
		}

		return user;
    }

	@Override
	public User clearBasket(User user) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		BaseDAO baseDAO = daoFactory.getBaseDAO();

		List<Book> basket = user.getBasket();
		try {
			for (Book book : basket) {
				book.setUser(null);
				baseDAO.update(book);
			}
			user.getBasket().clear();
			user = (User) baseDAO.update(user);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.FAILED_CLEAR_BASKET, e);
		}

		return user;
	}
}
