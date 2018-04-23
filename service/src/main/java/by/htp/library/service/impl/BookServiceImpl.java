package by.htp.library.service.impl;

import java.util.List;

import by.htp.library.dao.AuthorDAO;
import by.htp.library.entity.Book;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.User;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.helper.ServiceMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private AuthorDAO authorDAO;
	@Autowired
	private BookDAO bookDAO;

	//ADDING A BOOK
	@Override
	@Transactional
	public void addBook(Book book) throws ServiceException {
		//validation
		if((book.getAuthors() == null || book.getAuthors().isEmpty()) || 
				(book.getTitle() == null || book.getTitle().isEmpty())) {
			throw new ServiceException(ServiceMessages.INVALID_BOOK_PARAMETERS);
		}
		
		try {
			bookDAO.addBook(book);
		} catch (DAOException e) {
			throw new ServiceException(ServiceMessages.FAILURE_ADDING_BOOK, e);
		}
	}

	//������ �����!!!
	@Override
	@Transactional
	public List<Book> searchByTitle(String title) throws ServiceException {
		//validation
		if(title == null || title.isEmpty()) {
			return null;
		}
		
		List<Book> books;
		
		try {
			books = bookDAO.searchBookByTitle(title);
		} catch (DAOException e) {
			throw new ServiceException("something wrong", e);
		}
		
		return books;
	}

	//GETTING A LIST OF ALL BOOKS
	@Override
	@Transactional
	public List<Book> getAllBooks(int pageNumber, int pageSize) throws ServiceException {
		List<Book> books;
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
	@Transactional
	public long getBookCount() throws ServiceException {
		long bookCount;
		try {
			bookCount = bookDAO.getBookCount();
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return bookCount;
	}

	@Override
	@Transactional
	public void addBookToBasket(Book book) throws ServiceException {
		try {
			bookDAO.addBookToBasket(book);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	@Transactional
	public void addBookToBasket(User user, Book book) throws ServiceException {
		for (Book bookFromBasket : user.getBasket()) {
			if (bookFromBasket.getId().equals(book.getId()))
				throw new ServiceException(ServiceMessages.BOOK_ALREADY_IN_BASKET);
		}
		book.setUser(user);
		user.getBasket().add(book);
		try {
			bookDAO.updateBook(book);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}



	@Override
	@Transactional
	public void removeBookFromBasket(Book book) throws ServiceException {
		try {
			bookDAO.addBookToBasket(book);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public void removeBookFromBasket(User user, Book book) throws ServiceException {
		book.setUser(null);
		List<Book> basket = user.getBasket();
		for (int i = 0; i < basket.size(); i++) {
			if (basket.get(i).getId().equals(book.getId())) {

			}
		}
	}

}
