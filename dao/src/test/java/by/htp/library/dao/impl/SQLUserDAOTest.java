package by.htp.library.dao.impl;

import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.User;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class SQLUserDAOTest {
    private static EntityManager em;
    private static UserDAO userDAO;

    @BeforeClass
    public static void init() {
        em = EMUtil.getEntityManager("by.htp.library");
        userDAO = DAOFactory.getInstance().getUserDAO();

        User user = new User(null, "Stepchik", "123456", UserHelper.TYPE_ADMIN,
                UserHelper.STATUS_ACTIVE, null);
        UserData data = new UserData(null, "Stepan", "Stepanov", "stepchik@mail.ru", 0, user);
        user.setUserData(data);

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Test
    public void isUserExistTest() {
        boolean userExist;
        boolean userNotExist;
        try {
            userExist = userDAO.isUserExist("Stepchik");
            Assert.assertTrue(userExist);
            userNotExist = userDAO.isUserExist("superman");
            Assert.assertFalse(userNotExist);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginationTest() {
        User user = null;
        try {
            user = userDAO.logination("Stepchik", "123456");
        } catch (DAOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(user);
    }

    @Test(expected = DAOException.class)
    public void loginationFailedTest() throws DAOException {
        User user = userDAO.logination("Stepchik", "456321");
        Assert.assertNotNull(user);
    }

    @Test
    public void registartionTest() {
        User user = new User(null, "Ivanchik", "123456", UserHelper.TYPE_ADMIN,
                UserHelper.STATUS_ACTIVE, null);
        UserData data = new UserData(null, "Ivan", "Ivanov", "ivanchik@mail.ru", 0, user);
        user.setUserData(data);

        try {
            userDAO.registration(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeUserTest() {
        try {
            userDAO.removeUser("Stepchik");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findUserByLoginTest() {
        User user = null;
        try {
            user = userDAO.findUserByLogin("Stepchik");
        } catch (DAOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(user);
        System.out.println(user);
    }

    @Test (expected = DAOException.class)
    public void blockUnlockUserTest() throws DAOException {
        userDAO.blockUnlockUser("Stepchik");
    }

}
