package by.htp.library.dao;

import java.util.List;

import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.dao.exception.DAOException;

public interface BookDAO extends BaseDAO<Book> {
	
	void addBook(Book book)throws DAOException;
	List<Book> searchBookByTitle(String title) throws DAOException;
	List<Book> getAllBooks(int pageNumber, int pageSize) throws DAOException;
	void removeBook(String title, List<Author> authors) throws DAOException;
	void removeBook(Book book) throws DAOException;
	boolean isBookExist(String title, List<Author> authors) throws DAOException;
	long getBookCount() throws DAOException;
	void addBookToBasket(Book book) throws DAOException;
	void removeBookFromBasket(Book book) throws DAOException;
	Book updateBook(Book book) throws DAOException;

}
