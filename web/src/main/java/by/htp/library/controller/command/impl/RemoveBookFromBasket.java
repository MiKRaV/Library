package by.htp.library.controller.command.impl;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.*;
import by.htp.library.entity.User;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveBookFromBasket implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);

        int bookID = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));

        String goToPage = "";
        String message = "";
        String url = "";

        try {
            user = userService.removeBookFromBasket(user, bookID);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = Messages.BOOK_REMOVED_FROM_BASKET;
        } catch (ServiceException e) {
            e.printStackTrace();
            message = Messages.BOOK_NOT_REMOVED_FROM_BASKET + " : " + e.getMessage();
        }

        request.setAttribute(SessionAttributes.MESSAGE, message);

        goToPage = WebHelper.pageGenerator(Pages.BASKET);
        url = WebHelper.urlGenerator(request, CommandName.REMOVE_BOOK_FROM_BASKET);
        request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
        request.getSession().setAttribute(SessionAttributes.URL, url);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
