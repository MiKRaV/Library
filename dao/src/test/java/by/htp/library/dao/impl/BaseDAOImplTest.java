package by.htp.library.dao.impl;

import by.htp.library.dao.BaseDAO;
import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.BaseDAOHelper;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BaseDAOImplTest {
    BaseDAO baseDao;
    EntityManager em;

    @Before
    public void init() {
        em = EMUtil.getEntityManager("by.htp.library.test");
        baseDao = DAOFactory.getInstance().getBaseDAO();

        User user1 = new User(null, "Stepchik", "123456", UserHelper.TYPE_ADMIN,
                UserHelper.STATUS_ACTIVE, null, new ArrayList<>());
        UserData userData1 = new UserData(null, "Stepan", "Stepanov", "stepchik@mail.ru", user1);
        user1.setUserData(userData1);

        User user2 = new User(null, "Ivanchik", "123456", UserHelper.TYPE_READER,
                UserHelper.STATUS_ACTIVE, null, new ArrayList<>());
        UserData userData2 = new UserData(null, "Ivan", "Ivanov", "ivanchik@mail.ru", user2);
        user2.setUserData(userData2);

        User user3 = new User(null, "Vovchik", "123456", UserHelper.TYPE_READER,
                UserHelper.STATUS_ACTIVE, null, new ArrayList<>());
        UserData userData3 = new UserData(null, "Vladimir", "Vladimirov", "vovchik@mail.ru", user3);
        user3.setUserData(userData3);

        em.getTransaction().begin();
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.getTransaction().commit();

        em.clear();
    }

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
            baseDao.add(book);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        Book bookFromDB = em.find(Book.class, book.getId());
        Assert.assertEquals(book.getTitle(), bookFromDB.getTitle());

        em.clear();
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
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.clear();
        Book bookFromDB;
        try {
            bookFromDB = (Book) baseDao.find(Book.class, book.getId());
            Assert.assertNotNull(bookFromDB);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        em.clear();
    }

    @Test
    public void findAllTest() {
        List<User> users = null;
        try {
            users = baseDao.findAll(User.class, 1, 2);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        for (User user : users) {
            System.out.println(user + " " + user.getUserData());
        }
    }

    @Test
    public void updateTest() {
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
            baseDao.update(book);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Book bookFromDB = em.find(Book.class, book.getId());
        Author authorFromDB = bookFromDB.getAuthors().get(0);
        Assert.assertEquals("Gosha", authorFromDB.getName());

        em.clear();
    }

    @Test
    public void removeTest() {
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
            baseDao.remove(bookFromBD);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        em.clear();
        bookFromBD = em.find(Book.class, bookID);
        Assert.assertNull(bookFromBD);

        em.clear();
    }

    @Test
    public void getCountTest() {
        long userCount = 0;
        try {
            userCount = baseDao.getCount(User.class);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(userCount, 3);
    }
}
