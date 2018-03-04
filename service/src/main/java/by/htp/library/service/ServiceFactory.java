package by.htp.library.service;

import by.htp.library.service.impl.BookServiceImpl;
import by.htp.library.service.impl.UserServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	
	private final UserService userService = new UserServiceImpl();
	private final BookService bookService = new BookServiceImpl();
	
	private ServiceFactory() {}
	
	public UserService getUserService() {
		return userService;
	}
	
	public BookService getBookService() {
		return bookService;
	}
	
	
	public static ServiceFactory getInstance() {
		return instance;
	}

}
