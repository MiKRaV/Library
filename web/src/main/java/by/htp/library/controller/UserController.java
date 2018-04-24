package by.htp.library.controller;

import by.htp.library.controller.exception.WebException;
import by.htp.library.controller.helper.*;
import by.htp.library.entity.Book;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import by.htp.library.entity.helper.UserParameters;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/start-page", method = RequestMethod.GET)
    public String startPage() {
        return "start-page";
    }

    @RequestMapping(value = "/main-page", method = RequestMethod.POST)
    public String mainPage(@RequestParam("login") String login,
                           @RequestParam("password") String password,
                           HttpServletRequest request) {
        String userType = "";
        try {
            User user = userService.logination(login, password);
            userType = user.getType();
            request.getSession().setAttribute(SessionAttributes.USER, user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (userType.equals("admin"))
            return "admin-main-page";
        else if (userType.equals("reader"))
            return "reader-main-page";
        else
            return "start-page";
    }

    @RequestMapping(value = "/main-page", method = RequestMethod.GET)
    public String getMainPage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        if (user != null) {
            if (user.getType().equals("admin"))
                return "admin-main-page";
            else if (user.getType().equals("reader"))
                return "reader-main-page";
        }
        return HttpStatus.valueOf(404).toString();
    }

    @RequestMapping(value = "/start-page", method = RequestMethod.POST)
    public String logOut(HttpServletRequest request) {
        request.getSession().removeAttribute(SessionAttributes.USER);
        return "start-page";
    }

    @RequestMapping(value = "/user-data", method = RequestMethod.GET)
    public String userData(HttpServletRequest request) {
        return "user-data";
    }

    @RequestMapping(value = "/user-data", method = RequestMethod.POST)
    public String changeData(HttpServletRequest request, ModelAndView model) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        String userParameter = request.getParameter(RequestParameters.USER_PARAMETER);
        UserParameters parameter = null;
        String newValue = null;
        String message = "";

        try {
            switch (userParameter) {
                case RequestParameters.USER_PASSWORD:
                    String oldPassword = request.getParameter(RequestParameters.USER_OLD_PASSWORD);
                    String newPassword = request.getParameter(RequestParameters.USER_NEW_PASSWORD);
                    String confirmPassword = request.getParameter(RequestParameters.USER_CONFIRM_PASSWORD);
                    if(!oldPassword.equals(user.getPassword())) {
                        throw new WebException(Messages.INVALID_PASSWORD);
                    } else if (!newPassword.equals(confirmPassword)) {
                        throw new WebException(Messages.PASSWORDS_DO_NOT_MATCH);
                    }
                    parameter = UserParameters.PASSWORD;
                    newValue = newPassword;
                    break;
                case RequestParameters.USER_NAME:
                    parameter = UserParameters.NAME;
                    newValue = request.getParameter(RequestParameters.USER_NEW_NAME);
                    break;
                case RequestParameters.USER_SURNAME:
                    parameter = UserParameters.SURNAME;
                    newValue = request.getParameter(RequestParameters.USER_NEW_SURNAME);
                    break;
                case RequestParameters.USER_EMAIL:
                    parameter = UserParameters.EMAIL;
                    newValue = request.getParameter(RequestParameters.USER_NEW_EMAIL);
                    break;
            }
            user = userService.updateUser(user, parameter, newValue);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = parameter.getParameter().toUpperCase() + " changed successfully!";
        } catch (ServiceException e) {
            e.printStackTrace();
            message = e.getMessage();
        } catch (WebException e) {
            e.printStackTrace();
            message = e.getMessage();
        }

        model.addObject("message", message);

        return "user-data";
    }

    @RequestMapping(value = "/users-list", method = RequestMethod.GET)
    public String getAllUsers(HttpServletRequest request, ModelMap model) {
        List<User> userList = null;
        int pageSize = 10;
        int page = 1;
        long userCount;
        int pageCount;

        try {
            if (request.getAttribute(RequestAttributes.CURRENT_PAGE) != null)
                page = (int) request.getAttribute(RequestAttributes.CURRENT_PAGE);
            else if (request.getParameter(RequestParameters.PAGE) != null)
                page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));

            if (request.getParameter(RequestParameters.PAGE_SIZE) != null)
                pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));

            userCount = userService.getUserCount();
            pageCount = (int) Math.ceil(userCount * 1.0 / pageSize);

            userList = userService.getAllUsersList(page, pageSize);

            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPage", page);
            model.addAttribute("userList", userList);
            model.addAttribute("pageSize", pageSize);
        } catch (ServiceException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }

        return "users";
    }

    @RequestMapping(value = "/search-user", method = RequestMethod.GET)
    public String searchUserPage() {
        return "search-user";
    }

    @RequestMapping(value = "/found-user", method = RequestMethod.GET)
    public String foundUserPage() {
        return "found-user-data";
    }

    @RequestMapping(value = "/found-user", method = RequestMethod.POST)
    public String findUser(HttpServletRequest request, ModelMap model) {
        String login = request.getParameter(RequestParameters.USER_LOGIN);
        User foundUser = null;
        String errorMessage = "";
        String command = request.getParameter("command");
        try {
            if (command != null) {
                switch (command) {
                    case "blockUnlockUser":
                        blockUnlockUser(login);
                        break;
                    case "removeUser":
                        removeUser(login);
                        break;
                }
            }
            foundUser = userService.findUserByLogin(login);
            request.getSession().setAttribute(SessionAttributes.FOUND_USER, foundUser);
        } catch (ServiceException e) {
            e.printStackTrace();
            errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }
        return "found-user-data";
    }

    private void blockUnlockUser(String login) throws ServiceException {
        userService.blockUnlockUser(login);
    }

    private void removeUser(String login) throws ServiceException {
        userService.removeUser(login);
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String getBasket() {
        return "basket";
    }

    @RequestMapping(value = "/basket", method = RequestMethod.POST)
    public String basketAction(HttpServletRequest request, ModelMap model) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        String command = request.getParameter("command");
        String message = "";
        switch (command) {
            case "clearBasket":
                try {
                    user = userService.clearBasket(user);
                    request.getSession().setAttribute(SessionAttributes.USER, user);
                    message = Messages.BASKET_CLEARED_SUCCESSFULLY;
                } catch (ServiceException e) {
                    message = e.getMessage();
                }
                break;
            case "removeBookFromBasket":
                int bookID = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));
                try {
                    user = userService.removeBookFromBasket(user, bookID);
                    request.getSession().setAttribute(SessionAttributes.USER, user);
                    message = Messages.BOOK_REMOVED_FROM_BASKET;
                } catch (ServiceException e) {
                    e.printStackTrace();
                    message = Messages.BOOK_NOT_REMOVED_FROM_BASKET + " : " + e.getMessage();
                }
                break;
        }

        model.addAttribute("message", message);

        return "basket";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return "registration-page";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(HttpServletRequest request, ModelMap model) {
        String login = request.getParameter(RequestParameters.USER_LOGIN);
        String password = request.getParameter(RequestParameters.USER_PASSWORD);
        String name = request.getParameter(RequestParameters.USER_NAME);
        String surname = request.getParameter(RequestParameters.USER_SURNAME);
        String email = request.getParameter(RequestParameters.USER_EMAIL);
        String userType = request.getParameter(RequestParameters.USER_TYPE);
        String userStatus = UserHelper.STATUS_ACTIVE;
        List<Book> basket = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        User user = null;

        try {
            user = new User(null, login, password, userType, userStatus, null, basket, orders);
            UserData userData = new UserData(name, surname, email);
            user.setUserData(userData);
            userService.registration(user);
            request.getSession().setAttribute(SessionAttributes.USER, user);
        } catch (ServiceException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "registration-page";
        }

        return getMainPage(request);
    }
}
