package by.htp.library.pojos;

import by.htp.library.dao.util.HibernateUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.ArrayList;

public class UserTest {

    ArrayList<User> users = new ArrayList<>();

    @Before
    public void setUpUsers() {
        users.add(new User(null, "Stepchik", "123456", "Степан", "Степанов",
                "stepchik@gmail.com", "admin", 0, "active"));
        users.add(new User(null, "Ivanchik", "654321", "Иван", "Иванов",
                "ivanchik@gmail.com", "reader", 0, "active"));
        users.add(new User(null, "Svetik", "123456", "Света", "Светлова",
                "svetik@gmail.com", "reader", 0, "active"));
        users.add(new User(null, "Vladik", "123456", "Владислав", "Владов",
                "vladik@gmail.com", "reader", 0, "active"));
        users.add(new User(null, "Serg", "123456", "Сергей", "Сергеев",
                "serg@gmail.com", "reader", 0, "active"));
    }

    @Test
    public void userTest() {
        EntityManager em = HibernateUtil.getEntityManager();
        addUsersTest(em);
        em = HibernateUtil.getEntityManager();
        changeUserDataTest(em);
    }

    private void addUsersTest(EntityManager em) {
        try {
            for (User user : users) {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            }
        } catch (RollbackException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        em = HibernateUtil.getEntityManager();
        for (User user : users) {
            em.getTransaction().begin();
            User userFromDB = em.find(User.class, user.getId());
            Assert.assertEquals(user, userFromDB);
            em.getTransaction().commit();
        }
    }

    private void changeUserDataTest(EntityManager em) {
        int randomId = (int) (Math.random() * users.size());
        em.getTransaction().begin();
        User userFromDB = em.find(User.class, randomId);

        String login = userFromDB.getLogin();
        String password = userFromDB.getPassword();
        String newName = "Новое_Имя";
        String surname = userFromDB.getSurname();
        String email = userFromDB.getEmail();
        String userType = userFromDB.getUserType();
        int countBook = userFromDB.getCountBook();
        String userStatus = userFromDB.getUserStatus();

        em.getTransaction().commit();

        User modifiedUser = new User(randomId, login, password, newName, surname,
                email, userType, countBook, userStatus);

        try {
            em.getTransaction().begin();
            em.merge(modifiedUser);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        userFromDB = em.find(User.class, randomId);
        Assert.assertEquals(modifiedUser, userFromDB);
        em.getTransaction().commit();
    }

    @AfterClass
    public static void cleanUp() {
        HibernateUtil.close();
    }


}
