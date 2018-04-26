package by.htp.library;

import by.htp.library.dao.BookDAO;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.BookStatus;
import by.htp.library.entity.helper.Genre;
import by.htp.library.entity.helper.UserStatus;
import by.htp.library.entity.helper.UserType;
import by.htp.library.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-beans-services.xml")
@Transactional
public class OrderServiceImplTest {

    @Autowired
    UserDAO userDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    OrderService orderService;

    @Before
    public void init() {
        User user = new User(null, "Stepchik", "123456", UserType.ADMIN,
                UserStatus.ACTIVE, null, new ArrayList<>(), new ArrayList<>(), null);
        UserData data = new UserData("Stepan", "Stepanov", "stepchik@mail.ru");
        user.setUserData(data);

        Book book = new Book(null, "War and peace", Genre.FICTION, BookStatus.AVAILABLE, new ArrayList<>(), null, null, null);

        try {
            userDAO.add(user);
            bookDAO.add(book);
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Transactional
    public void createOrderTest() {
        Book book = null;
        User user = null;
        try {
            book = bookDAO.find(1);
            user = userDAO.find(1);
            user.getBasket().add(book);
            userDAO.add(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println(user);
        System.out.println(book);

    }
}
