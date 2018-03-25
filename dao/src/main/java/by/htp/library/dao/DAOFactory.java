package by.htp.library.dao;

import by.htp.library.dao.impl.AuthorDAOImpl;
import by.htp.library.dao.impl.BaseDAOImpl;
import by.htp.library.dao.impl.BookDAOImpl;
import by.htp.library.dao.impl.UserDAOImpl;

public class DAOFactory {
	
	private static final DAOFactory instance = new DAOFactory();
	
	private final BaseDAO baseDao = new BaseDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	private final BookDAO bookDAO = new BookDAOImpl();
	private final AuthorDAO authorDAO = new AuthorDAOImpl();
	
	private DAOFactory() {}

	public BaseDAO getBaseDAO() {return baseDao;}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public BookDAO getBookDAO() {
		return bookDAO;
	}

	public AuthorDAO getAuthorDAO() {return authorDAO;}
		
	public static DAOFactory getInstance() {
		return instance;
	}

}

