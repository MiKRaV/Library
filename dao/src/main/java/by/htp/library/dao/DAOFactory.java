package by.htp.library.dao;

import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.impl.SQLBookDAO;
import by.htp.library.dao.impl.SQLUserDAO;

public class DAOFactory {
	
	private static final DAOFactory instance = new DAOFactory();
	
	private final UserDAO userDAO = new SQLUserDAO();
	private final BookDAO bookDAO = new SQLBookDAO();
	
	private DAOFactory() {}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public BookDAO getBookDAO() {
		return bookDAO;
	}
		
	public static DAOFactory getInstance() throws DAOException {
		return instance;
	}

}

