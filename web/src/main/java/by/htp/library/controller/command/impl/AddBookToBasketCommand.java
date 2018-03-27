package by.htp.library.controller.command.impl;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.*;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddBookToBasketCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);

        List<Book> bookList = (List<Book>) request.getSession().getAttribute(SessionAttributes.BOOK_LIST);
        int bookID = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));
        int page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
        int pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));
        int pageCount = Integer.parseInt(request.getParameter(RequestParameters.PAGE_COUNT));

        String goToPage = "";
        String message = "";
        String url = "";

        try {
            user = userService.addBookToBasket(user, bookID);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = Messages.BOOK_ADDED_TO_BASKET;
        } catch (ServiceException e) {
            e.printStackTrace();
            message = Messages.BOOK_NOT_ADDED_TO_BASKET + " : " + e.getMessage();
        }

        request.setAttribute(RequestAttributes.PAGE_COUNT, pageCount);
        request.setAttribute(RequestAttributes.CURRENT_PAGE, page);
        request.setAttribute(RequestAttributes.BOOK_LIST, bookList);
        request.setAttribute(RequestAttributes.PAGE_SIZE, pageSize);

        request.setAttribute(SessionAttributes.MESSAGE, message);

        goToPage = WebHelper.pageGenerator(Pages.TABLE_WITH_BOOKS);
        url = WebHelper.urlGenerator(request, CommandName.GET_ALL_BOOKS);
        request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
        request.getSession().setAttribute(SessionAttributes.URL, url);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
