package by.htp.library.service.impl;

import java.util.ArrayList;

import by.htp.library.entity.User;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import by.htp.library.entity.UserParameters;
import by.htp.library.service.Validator;

public class UserServiceImpl implements UserService {
	
	private Validator validator = new Validator();

	//�����������
	@Override
	public User logination(String login, String password) throws ServiceException {
		// validation
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException("Incorrect login");
		} else if(!validator.validate(password, UserParameters.PASSWORD)) {
			throw new ServiceException("Incorrect password");
		}

		User user = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.logination(login, password);
		} catch (DAOException e) {
			throw new ServiceException("Logination failed: " + e.getMessage(), e);
		}

		return user;
	}

	//�����������
	@Override
	public void registration(User user) throws ServiceException {
		// validation
		if(!validator.validate(user.getLogin(), UserParameters.LOGIN)) {
			throw new ServiceException("Incorrect login");
		} else if(!validator.validate(user.getPassword(), UserParameters.PASSWORD)) {
			throw new ServiceException("Incorrect password");
		} else if(!validator.validate(user.getName(), UserParameters.NAME)) {
			throw new ServiceException("Incorrect name");
		} else if(!validator.validate(user.getSurname(), UserParameters.SURNAME)) {
			throw new ServiceException("Incorrect surname");
		} else if(!validator.validate(user.getEmail(), UserParameters.EMAIL)) {
			throw new ServiceException("Incorrect e-mail");
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.registration(user);
		} catch (DAOException e) {
			throw new ServiceException("Registration failed: " + e.getMessage(), e);
		}
	}
	
	//��������� ������ ���� �������������
	@Override
	public ArrayList<User> getAllUsersList() throws ServiceException {
		
		ArrayList<User> userList = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userList = userDAO.getAllUsersList();
		} catch (DAOException e) {
			throw new ServiceException("List of users not received: " + e.getMessage(), e);
		}
		return userList;
	}

	@Override//������ �����!!!
	public boolean isUserExist(String login) throws ServiceException {
		// validation
			if (login == null || login.isEmpty()) {
				throw new ServiceException("Invalid Login");
			}
		
		boolean isUserExist = false;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			isUserExist = userDAO.isUserExist(login);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return isUserExist;
	}

	//�������������� ������ ������������
	@Override//
	public void changeUserData(User user, UserParameters data, String dataValue) throws ServiceException {
		// validation	
		if (!validator.validate(dataValue, data)) {
			throw new ServiceException("Incorrect value");
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.changeUserData(user, data, dataValue);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
	}

	//�������� ������������
	@Override
	public void removeUser(String login) throws ServiceException {
		//validation
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException("Incorrect login");
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.removeUser(login);
		} catch (DAOException e) {
			throw new ServiceException("Removing failed: " + e.getMessage(), e);
		}
		
	}

	//����� ������������ �� ������
	@Override
	public User findUserByLogin(String login) throws ServiceException {
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException("Incorrect login");
		}
		
		User user = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			user = userDAO.findUserByLogin(login);
		} catch (DAOException e) {
			throw new ServiceException("User search failed: " + e.getMessage(), e);
		}
		
		return user;
	}

	@Override
	public void blockUnlockUser(String login) throws ServiceException {
		if(!validator.validate(login, UserParameters.LOGIN)) {
			throw new ServiceException("Incorrect login");
		}
		
		DAOFactory daoFactory;
		try {
			daoFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoFactory.getUserDAO();
			userDAO.blockUnlockUser(login);
		} catch (DAOException e) {
			throw new ServiceException("User lock/unlock operation failed: " + e.getMessage(), e);
		}
		
	}
	
	

}
