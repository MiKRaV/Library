package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.htp.library.dao.helper.BookDAOHelper;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.db.ConnectionPool;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.AuthorHelper;
import by.htp.library.entity.helper.BookHelper;
import by.htp.library.entity.helper.UserHelper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookDAOImpl implements BookDAO {

	private by.htp.library.dao.BaseDAO baseDAO = new BaseDAOImpl();
	private EntityManager em = EMUtil.getEntityManager();
	private CriteriaBuilder cb = em.getCriteriaBuilder();
	private ConnectionPool conPool;
			//conPool = ConnectionPool.getConnectionPool();

	public void addBook(Book book) throws DAOException {
		if (isBookExist(book.getTitle(), book.getAuthors())) {
			throw new DAOException(BookDAOHelper.MESSAGE_BOOK_ALREADY_EXIST);
		}

		baseDAO.add(book);
	}

	public List<Book> searchBookByTitle(String title) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getAllBooks() throws DAOException {
		return null;
	}

	//GETTING A LIST OF ALL BOOKS
	public List<Book> getAllBooks(int pageNumber, int pageSize) throws DAOException {
		return baseDAO.findAll(Book.class, pageNumber, pageSize);
	}

	//CHECK IF THE AUTHOR EXISTS
	public boolean isAuthorExist(String name, String surname) throws DAOException {
		CriteriaQuery<Author> criteria = cb.createQuery(Author.class);
		Root<Author> root = criteria.from(Author.class);
		Predicate predicate = cb.and(
				cb.equal(root.get(AuthorHelper.NAME), name),
				cb.equal(root.get(AuthorHelper.SURNAME), surname)
		);
		criteria.select(root).where(predicate);
		try {
			em.createQuery(criteria).getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}



	//УДАЛЕНИЕ КНИГИ
	public void removeBook(String title, List<Author> authors) throws DAOException {
		if(!isBookExist(title, authors)) {
			throw new DAOException("Such a book does not exist");
		}
		
		
	}

	@Override
	public void removeBook(Book book) throws DAOException {

	}

	//CHECK IF THE BOOK EXISTS
	public boolean isBookExist(String title, List<Author> authors) throws DAOException {
		CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);
		criteria.select(root)
				.where(cb.equal(root.get(BookHelper.TITLE), title));
		List<Book> books;
		try {
			books = em.createQuery(criteria).getResultList();
		} catch (NoResultException e) {
			return false;
		}

		for (Author author : authors) {
			if (!isAuthorExist(author.getName(), author.getSurname()))
				return false;
		}

		for (Book book : books) {
			List<Author> authorsFromBook = book.getAuthors();
			if (book.getAuthors().size() != authors.size())
				break;
			int matchesNumber = 0;
			for (int i = 0; i < authors.size(); i++) {
				String expectedAuthorName = authors.get(i).getName();
				String expectedAuthorSurname = authors.get(i).getSurname();
				for (int j = 0; j < authorsFromBook.size(); j++) {
					String actualAuthorName = authorsFromBook.get(j).getName();
					String actualAuthorSurname = authorsFromBook.get(j).getSurname();
					if (expectedAuthorName.equals(actualAuthorName)
							&& expectedAuthorSurname.equals(actualAuthorSurname)) {
						authorsFromBook.remove(j);
						matchesNumber++;
						break;
					}
				}
			}
			if (matchesNumber == authors.size())
				return true;
		}
		return false;
	}
}
