package by.htp.library.pojo;

import by.htp.library.dao.util.EMUtil;
import by.htp.library.pojo.Genre;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class GenreTest {

    @Test
    public void crudTest() {
        EntityManager em = EMUtil.getEntityManager("by.htp.library.test");
        Genre genre = new Genre("Science fiction");
        em.getTransaction().begin();
        em.persist(genre);
        em.getTransaction().commit();
        em.clear();
        Genre genreFromDB = em.find(Genre.class, genre.getId());
        Assert.assertEquals(genreFromDB.getGenre(), genre.getGenre());

        genreFromDB.setGenre("Encyclopedia");
        em.getTransaction().begin();
        em.merge(genreFromDB);
        em.getTransaction().commit();
        em.clear();
        genreFromDB = em.find(Genre.class, genre.getId());
        Assert.assertEquals(genreFromDB.getGenre(), "Encyclopedia");

        em.getTransaction().begin();
        em.remove(genreFromDB);
        em.getTransaction().commit();
        em.clear();
        genreFromDB = em.find(Genre.class, genre.getId());
        Assert.assertNull(genreFromDB);

        em.close();
    }
}
