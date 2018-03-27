package by.htp.library.entity;

import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.helper.UserHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class UserTest {

    @Test
    public void crudTest() {
        EntityManager em = EMUtil.getEntityManager("by.htp.library.test");
        em.getTransaction().begin();
        User user = new User(null,"MiKRaV", "123456", UserHelper.TYPE_ADMIN,
                UserHelper.STATUS_ACTIVE, null, new ArrayList<>());
        UserData userData = new UserData(null, "Mikhail", "Kravchenya",
                "mkravchenya@mail.ru",  user);
        user.setUserData(userData);
        em.persist(user);
        em.getTransaction().commit();
        em.clear();
        int userID = user.getId();
        User userFromDB = em.find(User.class, userID);
        Assert.assertEquals(user.getLogin(), userFromDB.getLogin());

        userFromDB.getUserData().setName("Gosha");
        em.getTransaction().begin();
        em.merge(userFromDB);
        em.getTransaction().commit();
        em.clear();
        Assert.assertEquals("Gosha", userFromDB.getUserData().getName());

        userFromDB.getBasket().add(new Book(null, "1", "fiction", null, null));
        em.getTransaction().begin();
        em.merge(userFromDB);
        em.getTransaction().commit();
        em.clear();
        userFromDB = em.find(User.class, userID);
        System.out.println(userFromDB.getBasket().size());

        em.getTransaction().begin();
        userFromDB = em.find(User.class, userID);
        em.remove(userFromDB);
        em.getTransaction().commit();
        em.clear();
        userFromDB = em.find(User.class, userID);
        Assert.assertNull(userFromDB);

        em.close();
    }


    @AfterClass
    public static void cleanUp() {
        EMUtil.closeEMFactory();
    }


}
