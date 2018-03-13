package by.htp.library.dao.impl;

import by.htp.library.dao.DAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.pojo.Author;
import by.htp.library.pojo.Book;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class DAOImplTest {
    DAO dao = new DAOImpl();
    EntityManager em;

    @Test
    public void addTest() {
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("Head First Java");
        books.add(book);
        Author author1 = new Author(null, "Kathy", "Sierra", books);
        Author author2 = new Author(null, "Bert", "Bates", books);
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);
        try {
            dao.add(book);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        em = EMUtil.getEntityManager("by.htp.library.test");
        Book bookFromDB = em.find(Book.class, book.getId());
        Assert.assertEquals(book.getTitle(), bookFromDB.getTitle());

        em.close();
    }

    @Test
    public void findTest() {

        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("Head First Java");
        books.add(book);
        Author author1 = new Author(null, "Kathy", "Sierra", books);
        Author author2 = new Author(null, "Bert", "Bates", books);
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);
        em = EMUtil.getEntityManager("by.htp.library.test");
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.clear();
        Book bookFromDB;
        try {
            bookFromDB = (Book) dao.find(Book.class, book.getId());
            Assert.assertNotNull(bookFromDB);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        em.close();
    }

    @Test
    public void changeTest() {
        em = EMUtil.getEntityManager("by.htp.library.test");
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("Head First Java");
        books.add(book);
        Author author1 = new Author(null, "Kathy", "Sierra", books);
        Author author2 = new Author(null, "Bert", "Bates", books);
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);
        em = EMUtil.getEntityManager("by.htp.library.test");
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.clear();
        book.getAuthors().get(0).setName("Gosha");
        try {
            dao.change(book);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Book bookFromDB = em.find(Book.class, book.getId());
        Author authorFromDB = bookFromDB.getAuthors().get(0);
        Assert.assertEquals("Gosha", authorFromDB.getName());

        em.close();
    }

    @Test
    public void removeTest() {
        em = EMUtil.getEntityManager("by.htp.library.test");
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book("Head First Java");
        books.add(book);
        Author author1 = new Author(null, "Kathy", "Sierra", books);
        Author author2 = new Author(null, "Bert", "Bates", books);
        book.getAuthors().add(author1);
        book.getAuthors().add(author2);
        em = EMUtil.getEntityManager("by.htp.library.test");
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.clear();
        Integer bookID = book.getId();
        Book bookFromBD = em.find(Book.class, bookID);
        try {
            dao.remove(bookFromBD);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        em.clear();
        bookFromBD = em.find(Book.class, bookID);
        Assert.assertNull(bookFromBD);

        em.close();
    }
}
