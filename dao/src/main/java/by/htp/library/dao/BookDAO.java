package by.htp.library.dao;

import java.util.List;

import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.dao.exception.DAOException;

public interface BookDAO {
	
	void addBook(Book book)throws DAOException;
	List<Book> searchBookByTitle(String title) throws DAOException;
	List<Book> getAllBooks() throws DAOException;
	List<Book> getAllBooks(int pageNumber, int pageSize) throws DAOException;
	boolean isAuthorExist(String name, String surname) throws DAOException;
	void removeBook(String title, List<Author> authors) throws DAOException;
	void removeBook(Book book) throws DAOException;
	boolean isBookExist(String title, List<Author> authors) throws DAOException;

}
