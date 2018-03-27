package by.htp.library.service;

import java.util.List;

import by.htp.library.entity.Book;
import by.htp.library.entity.User;

public interface BookService {
	
	void addBook(Book book) throws ServiceException;
	List<Book> searchByTitle(String title) throws ServiceException;
	List<Book> getAllBooks(int pageNumber, int pageSize) throws ServiceException;
	boolean isAuthorExist(String name, String surname) throws ServiceException;
	long getBookCount() throws ServiceException;
	void addBookToBasket(Book book) throws ServiceException;
	void addBookToBasket(User user, Book book) throws ServiceException;
	void removeBookFromBasket(Book book) throws ServiceException;
	void removeBookFromBasket(User user, Book book) throws ServiceException;
}
