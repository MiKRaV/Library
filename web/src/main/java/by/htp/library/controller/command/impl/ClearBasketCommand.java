package by.htp.library.controller.command.impl;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.Messages;
import by.htp.library.controller.helper.Pages;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;
import by.htp.library.entity.User;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClearBasketCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        String message = "";
        String goToPage = WebHelper.pageGenerator(Pages.BASKET);
        request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);

        try {
            user = userService.clearBasket(user);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = Messages.BASKET_CLEARED_SUCCESSFULLY;
            request.getSession().setAttribute(SessionAttributes.USER, user);
        } catch (ServiceException e) {
            message = e.getMessage();
        }

        request.setAttribute(SessionAttributes.MESSAGE, message);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
