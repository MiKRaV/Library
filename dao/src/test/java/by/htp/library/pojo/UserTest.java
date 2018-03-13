package by.htp.library.pojo;

import by.htp.library.dao.util.EMUtil;
import by.htp.library.pojo.helper.UserHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class UserTest {

    @Test
    public void crudTest() {
        EntityManager em = EMUtil.getEntityManager("by.htp.library.test");
        em.getTransaction().begin();
        User user = new User("MiKRaV", "123456", UserHelper.TYPE_ADMIN, UserHelper.STATUS_ACTIVE, null);
        UserData userData = new UserData(null, "Mikhail", "Kravchenya",
                "mkravchenya@mail.ru", 0,  user);
        user.setUserData(userData);
        em.persist(user);
        em.getTransaction().commit();
        em.clear();
        String userID = user.getLogin();
        User userFromDB = em.find(User.class, userID);
        Assert.assertEquals(user.getLogin(), userFromDB.getLogin());

        userFromDB.getUserData().setName("Gosha");
        em.getTransaction().begin();
        em.merge(userFromDB);
        em.getTransaction().commit();
        em.clear();
        Assert.assertEquals("Gosha", userFromDB.getUserData().getName());

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
