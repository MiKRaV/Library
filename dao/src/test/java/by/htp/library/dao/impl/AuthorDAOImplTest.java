package by.htp.library.dao.impl;

import by.htp.library.dao.AuthorDAO;
import by.htp.library.dao.BookDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImplTest {

    private static EntityManager em = EMUtil.getEntityManager("by.htp.library.test");;
    private static CriteriaBuilder cb = em.getCriteriaBuilder();
    private static AuthorDAO authorDAO = new AuthorDAOImpl();
    private static BookDAO bookDAO = new BookDAOImpl();

    @BeforeClass
    public static void init() {
        List<Book> pushkinBooks = new ArrayList<>();
        Author pushkin = new Author(null, "Pushkin", "Aleksandr", pushkinBooks);
        pushkinBooks.add(new Book());

        em.getTransaction().begin();
        em.persist(pushkin);
        em.getTransaction().commit();
    }

    @Test
    public void isAuthorExistTest() {
        boolean authorExist;
        boolean authorNotExist;
        try {
            authorExist = authorDAO.isAuthorExist("Aleksandr", "Pushkin");
            Assert.assertTrue(authorExist);
            authorNotExist = authorDAO.isAuthorExist("Aleksandr", "NePuskin");
            Assert.assertFalse(authorNotExist);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
