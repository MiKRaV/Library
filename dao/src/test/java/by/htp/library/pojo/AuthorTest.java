package by.htp.library.pojo;

import by.htp.library.dao.util.EMUtil;
import by.htp.library.pojo.Author;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class AuthorTest {

    @Test
    public void crudTest() {
        EntityManager em = EMUtil.getEntityManager("by.htp.library.test");
        Author author = new Author(null, "Kathy", "Sierra", null);
        em.getTransaction().begin();
        em.persist(author);
        em.getTransaction().commit();
        em.clear();
        Author authorFromDB = em.find(Author.class, author.getId());
        Assert.assertEquals(authorFromDB.getSurname(), author.getSurname());

        authorFromDB.setName("Gosha");
        em.getTransaction().begin();
        em.merge(authorFromDB);
        em.getTransaction().commit();
        em.clear();
        authorFromDB = em.find(Author.class, author.getId());
        Assert.assertEquals(authorFromDB.getName(), "Gosha");

        em.getTransaction().begin();
        em.remove(authorFromDB);
        em.getTransaction().commit();
        em.clear();
        authorFromDB = em.find(Author.class, author.getId());
        Assert.assertNull(authorFromDB);

        em.close();
    }
}
