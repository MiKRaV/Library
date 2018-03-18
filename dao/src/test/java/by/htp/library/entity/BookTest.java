package by.htp.library.entity;

import by.htp.library.dao.util.EMUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class BookTest {

    @Test
    public void crudTest() {
        EntityManager em = EMUtil.getEntityManager("by.htp.library.test");
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
        Book bookFromDB = em.find(Book.class, book.getId());
        Assert.assertEquals(book.getTitle(), bookFromDB.getTitle());

        em.getTransaction().begin();
        bookFromDB.getAuthors().get(0).setName("Gosha");
        em.merge(bookFromDB);
        em.getTransaction().commit();
        em.clear();
        bookFromDB = em.find(Book.class, book.getId());
        Author authorFromDB = bookFromDB.getAuthors().get(0);
        Assert.assertEquals("Gosha", authorFromDB.getName());

        em.getTransaction().begin();
        bookFromDB.getAuthors().remove(authorFromDB);
        em.getTransaction().commit();

        em.close();
    }
}
