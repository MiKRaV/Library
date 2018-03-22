package by.htp.library.dao.impl;

import by.htp.library.dao.BookDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BooKDAOImplTest {

    private static EntityManager em;
    private static BookDAO bookDAO;

    @BeforeClass
    public static void init() {
        em = EMUtil.getEntityManager("by.htp.library");
        bookDAO = DAOFactory.getInstance().getBookDAO();

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
    public void isAuthorExistTest() {
        boolean authorExist;
        boolean authorNotExist;
        try {
            authorExist = bookDAO.isAuthorExist("Aleksandr", "Pushkin");
            Assert.assertTrue(authorExist);
            authorNotExist = bookDAO.isAuthorExist("Aleksandr", "NePuskin");
            Assert.assertFalse(authorNotExist);
        } catch (DAOException e) {
            e.printStackTrace();
        }
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
