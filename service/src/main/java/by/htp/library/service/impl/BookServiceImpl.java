package by.htp.library.service.impl;

import java.util.List;

import by.htp.library.entity.Book;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;

public class BookServiceImpl implements BookService{

	//���������� �����
	@Override
	public void addBook(Book book) throws ServiceException {
		//validation
		if((book.getAuthors() == null || book.getAuthors().isEmpty()) || 
				(book.getTitle() == null || book.getTitle().isEmpty())) {
			throw new ServiceException("Invalid parameters of the book");
		}
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			BookDAO bookDAO = daoFactory.getBookDAO();
			bookDAO.addBook(book);
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
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

	@Override
	public List<Book> getAllBooks() throws ServiceException {
		
		List<Book> books = null;
		
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			BookDAO bookDAO = daoFactory.getBookDAO();
			books = bookDAO.getAllBooks();
		} catch (DAOException e) {
			throw new ServiceException("smth wrong", e);
		}
		
		return books;
	}

	@Override
	public boolean isAuthorExist(String name, String surname) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
