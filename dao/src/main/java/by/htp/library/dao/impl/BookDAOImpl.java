package by.htp.library.dao.impl;

import java.util.List;

import by.htp.library.dao.AuthorDAO;
import by.htp.library.dao.BaseDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.helper.BookDAOHelper;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.db.ConnectionPool;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.helper.BookHelper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class BookDAOImpl extends BaseDAOImpl<Book> implements BookDAO {

	@Autowired
	private AuthorDAO authorDAO;

	@PersistenceContext
	@Getter
	private EntityManager em;
	private CriteriaBuilder cb;

	public BookDAOImpl() {
		super();
		clazz = Book.class;
	}

	public void addBook(Book book) throws DAOException {
	    if (isBookExist(book.getTitle(), book.getAuthors())) {
			throw new DAOException(BookDAOHelper.MESSAGE_BOOK_ALREADY_EXIST);
		}

		List<Author> authors = book.getAuthors();
		for (int i = 0; i < authors.size(); i++) {
			String name = authors.get(i).getName();
			String surname = authors.get(i).getSurname();
			if (authorDAO.isAuthorExist(name, surname)) {
				Author authorFromDB = authorDAO.getAuthor(name, surname);
				authorFromDB.getBooks().add(book);
				authors.set(i, authorFromDB);
			}
		}

		update(book);
	}

	public List<Book> searchBookByTitle(String title) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	//GETTING A LIST OF ALL BOOKS
	public List<Book> getAllBooks(int pageNumber, int pageSize) throws DAOException {
	    return findAll(pageNumber, pageSize);
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
        cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> criteria = cb.createQuery(clazz);
		Root<Book> root = criteria.from(clazz);
		criteria.select(root)
				.where(cb.equal(root.get(BookHelper.TITLE), title));
		List<Book> books;
		try {
			books = em.createQuery(criteria).getResultList();
		} catch (NoResultException e) {
			return false;
		}

		for (Author author : authors) {
			if (!authorDAO.isAuthorExist(author.getName(), author.getSurname()))
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

	//GETTING BOOK COUNT
	@Override
	public long getBookCount() throws DAOException {
		return getCount();
	}

	@Override
	public void addBookToBasket(Book book) throws DAOException {
		update(book);
	}

	@Override
	public void removeBookFromBasket(Book book) throws DAOException {
		update(book);
	}

	@Override
	public Book updateBook(Book book) throws DAOException {
		return update(book);
	}
}
