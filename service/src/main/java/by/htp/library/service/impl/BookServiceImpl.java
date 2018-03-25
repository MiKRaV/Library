package by.htp.library.service.impl;

import java.util.List;

import by.htp.library.entity.Book;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.helper.ServiceMessages;

public class BookServiceImpl implements BookService{

	//ADDING A BOOK
	@Override
	public void addBook(Book book) throws ServiceException {
		//validation
		if((book.getAuthors() == null || book.getAuthors().isEmpty()) || 
				(book.getTitle() == null || book.getTitle().isEmpty())) {
			throw new ServiceException(ServiceMessages.INVALID_BOOK_PARAMETERS);
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			BookDAO bookDAO = daoFactory.getBookDAO();
			bookDAO.addBook(book);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.FAILURE_ADDING_BOOK, e);
		}
	}

	//������ �����!!!
	@Override
	public List<Book> searchByTitle(String title) throws ServiceException {
		//validation
		if(title == null || title.isEmpty()) {
			return null;
		}
		
		List<Book> books = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			BookDAO bookDAO = daoFactory.getBookDAO();
			books = bookDAO.searchBookByTitle(title);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return books;
	}

	//GETTING A LIST OF ALL BOOKS
	@Override
	public List<Book> getAllBooks(int pageNumber, int pageSize) throws ServiceException {
		List<Book> books = null;
		DAOFactory daoFactory = DAOFactory.getInstance();
		BookDAO bookDAO = daoFactory.getBookDAO();
		try {
			books = bookDAO.getAllBooks(pageNumber, pageSize);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.BOOK_LIST_NOT_RECEIVED, e);
		}
		return books;
	}

	@Override
	public boolean isAuthorExist(String name, String surname) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	//GETTING BOOK COUNT
	@Override
	public long getBookCount() throws ServiceException {
		long bookCount;
		DAOFactory daoFactory;
		try {
			daoFactory = DAOFactory.getInstance();
			BookDAO bookDAO = daoFactory.getBookDAO();
			bookCount = bookDAO.getBookCount();
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return bookCount;
	}

}
