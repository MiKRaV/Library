package by.htp.library.dao;

import by.htp.library.dao.impl.*;

public class DAOFactory {
	
	private static final DAOFactory instance = new DAOFactory();
	
	private final BaseDAO baseDao = new BaseDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	private final BookDAO bookDAO = new BookDAOImpl();
	private final AuthorDAO authorDAO = new AuthorDAOImpl();
	public final OrderDAO orderDAO = new OrderDAOImpl();
	
	private DAOFactory() {}

	public BaseDAO getBaseDAO() {return baseDao;}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public BookDAO getBookDAO() {
		return bookDAO;
	}

	public AuthorDAO getAuthorDAO() {return authorDAO;}

	public OrderDAO getOrderDAO() {return orderDAO;}
		
	public static DAOFactory getInstance() {
		return instance;
	}

}

