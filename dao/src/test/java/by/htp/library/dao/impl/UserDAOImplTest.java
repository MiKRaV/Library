package by.htp.library.dao.impl;

import by.htp.library.dao.DAOFactory;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.util.EMUtil;
import by.htp.library.entity.User;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import by.htp.library.entity.helper.UserStatus;
import by.htp.library.entity.helper.UserType;
import lombok.Getter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-beans-dao.xml")
public class UserDAOImplTest {
    @Autowired
    private static UserDAO userDAO;
    @PersistenceContext
    @Getter
    private static EntityManager em ;

    @BeforeClass
    public static void init() {
        em = getEm();

        User user = new User(null, "Stepchik", "123456", UserType.ADMIN,
                UserStatus.ACTIVE, null, new ArrayList<>(), null, null);
        UserData data = new UserData("Stepan", "Stepanov", "stepchik@mail.ru");
        user.setUserData(data);

        getEm().getTransaction().begin();
        getEm().persist(user);
        getEm().getTransaction().commit();
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
        User user = new User(null, "Ivanchik", "123456", UserType.ADMIN,
                UserStatus.ACTIVE, null, new ArrayList<>(), null, null);
        UserData data = new UserData("Ivan", "Ivanov", "ivanchik@mail.ru");
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
