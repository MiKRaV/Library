package by.htp.library.controller;

import by.htp.library.controller.exception.WebException;
import by.htp.library.controller.helper.*;
import by.htp.library.entity.*;
import by.htp.library.entity.helper.UserParameters;
import by.htp.library.entity.helper.UserStatus;
import by.htp.library.entity.helper.UserType;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/start-page", method = RequestMethod.GET)
    public String startPage() {
        return Pages.START_PAGE;
    }

    @RequestMapping(value = "/main-page", method = RequestMethod.POST)
    public String mainPage(@RequestParam("login") String login,
                           @RequestParam("password") String password,
                           HttpServletRequest request) {
        UserType userType = null;
        try {
            User user = userService.logination(login, password);
            userType = user.getType();
            request.getSession().setAttribute(SessionAttributes.USER, user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        switch (userType) {
            case ADMIN: return Pages.ADMIN_MAIN_PAGE;
            case READER: return Pages.READER_MAIN_PAGE;
            default: return Pages.START_PAGE;
        }
    }

    @RequestMapping(value = "/main-page", method = RequestMethod.GET)
    public String getMainPage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        if (user != null) {
            UserType userType = user.getType();
            switch (userType) {
                case ADMIN: return Pages.ADMIN_MAIN_PAGE;
                case READER: return Pages.READER_MAIN_PAGE;
            }
        }
        return HttpStatus.valueOf(404).toString();
    }

    @RequestMapping(value = "/start-page", method = RequestMethod.POST)
    public String logOut(HttpServletRequest request) {
        request.getSession().removeAttribute(SessionAttributes.USER);
        return Pages.START_PAGE;
    }

    @RequestMapping(value = "/user-data", method = RequestMethod.GET)
    public String userData(HttpServletRequest request) {
        return Pages.USER_DATA;
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

        return Pages.USER_DATA;
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

        return Pages.USERS;
    }

    @RequestMapping(value = "/search-user", method = RequestMethod.GET)
    public String searchUserPage() {
        return Pages.SEARCH_USER;
    }

    @RequestMapping(value = "/found-user", method = RequestMethod.GET)
    public String foundUserPage() {
        return Pages.FOUND_USER_DATA;
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
        return Pages.FOUND_USER_DATA;
    }

    private void blockUnlockUser(String login) throws ServiceException {
        userService.blockUnlockUser(login);
    }

    private void removeUser(String login) throws ServiceException {
        userService.removeUser(login);
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String getBasket() {
        return Pages.BASKET;
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

        return Pages.BASKET;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage() {
        return Pages.REGISTRATION_PAGE;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(HttpServletRequest request, ModelMap model) {
        String login = request.getParameter(RequestParameters.USER_LOGIN);
        String password = request.getParameter(RequestParameters.USER_PASSWORD);
        String name = request.getParameter(RequestParameters.USER_NAME);
        String surname = request.getParameter(RequestParameters.USER_SURNAME);
        String email = request.getParameter(RequestParameters.USER_EMAIL);
        UserType userType = UserType.valueOf(request.getParameter(RequestParameters.USER_TYPE).toUpperCase());
        UserStatus userStatus = UserStatus.ACTIVE;
        List<Book> basket = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Note> subscription = new ArrayList<>();

        User user = null;

        try {
            user = new User(null, login, password, userType, userStatus, null, basket, orders, subscription);
            UserData userData = new UserData(name, surname, email);
            user.setUserData(userData);
            userService.registration(user);
            request.getSession().setAttribute(SessionAttributes.USER, user);
        } catch (ServiceException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return Pages.REGISTRATION_PAGE;
        }

        return getMainPage(request);
    }

    @RequestMapping(value = "/reader-books", method = RequestMethod.GET)
    public String readerBooks(HttpServletRequest request, ModelMap model) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        try {
            List<Note> subscription = userService.getSubscription(user);
            request.getSession().setAttribute("subscription", subscription);
        } catch (ServiceException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }
        return Pages.READER_BOOKS;
    }
}
