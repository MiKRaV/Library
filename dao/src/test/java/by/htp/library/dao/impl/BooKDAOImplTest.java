package by.htp.library.dao.impl;

import by.htp.library.dao.AuthorDAO;
import by.htp.library.dao.BaseDAO;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.DAOMessages;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.entity.helper.AuthorHelper;
import by.htp.library.entity.helper.BookStatus;
import by.htp.library.entity.helper.Genre;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BooKDAOImplTest {

    private static EntityManager em = EMUtil.getEntityManager("by.htp.library");;
    private static CriteriaBuilder cb = em.getCriteriaBuilder();
    private static AuthorDAO authorDAO = new AuthorDAOImpl();
    private static BookDAO bookDAO = new BookDAOImpl();

    @BeforeClass
    public static void init() {
        List<Book> pushkinBooks = new ArrayList<>();
        Author pushkin = new Author(null, "Pushkin", "Aleksandr", pushkinBooks);
        pushkinBooks.add(new Book());

        List<Book> books = new ArrayList<>();
        Book book = new Book("Head First Java");
        books.add(book);
        Author author1 = new Author(null, "Sierra", "Kathy", books);
        Author author2 = new Author(null, "Bates", "Bert", books);
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);

        em.getTransaction().begin();
        em.persist(pushkin);
        em.persist(book);
        em.getTransaction().commit();
    }

    @Test
    public void addBookTest() {
        List<Book> pushkinBooks = new ArrayList<>();
        Book mciry = new Book(null, "Mciry", Genre.FICTION, BookStatus.AVAILABLE, null, null, null, null);
        pushkinBooks.add(mciry);
        List<Author> authors = new ArrayList<>();
        Author pushkin = new Author(null, "Pushkin", "Aleksandr", pushkinBooks);
        authors.add(pushkin);
        mciry.setAuthors(authors);
        try {
            bookDAO.addBook(mciry);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Author author = null;
        CriteriaQuery<Author> criteria = cb.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        Predicate predicate = cb.and(
                cb.equal(root.get(AuthorHelper.NAME), pushkin.getName()),
                cb.equal(root.get(AuthorHelper.SURNAME), pushkin.getSurname())
        );
        criteria.select(root).where(predicate);
        author = em.createQuery(criteria).getSingleResult();

        //Assert.assertTrue(author.getBooks().size() > 1);
    }

    @Test
    public void isBookExistTest() {
        String title = "Head First Java";
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null,"Bates" , "Bert", null));
        authors.add(new Author(null, "Sierra", "Kathy", null));
        boolean bookExist;
        try {
            bookExist = bookDAO.isBookExist(title, authors);
            Assert.assertTrue(bookExist);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
