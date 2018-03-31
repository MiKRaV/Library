package by.htp.library.controller.command.impl;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.Messages;
import by.htp.library.controller.helper.Pages;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;
import by.htp.library.entity.Book;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.OrderStatus;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CreateOrderCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();
        UserService userService = serviceFactory.getUserService();

        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        List<Book> basket = user.getBasket();
        String goToPage = WebHelper.pageGenerator(Pages.BASKET);
        String message = "";

        Order order = new Order(null, LocalDateTime.now(), null, basket, user, OrderStatus.IN_PROCESSING);

        try {
            orderService.createOrder(order);
            user.getOrders().add(order);
            user = userService.clearBasket(user);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = Messages.ORDER_IS_PROCESSED;
        } catch (ServiceException e) {
            message = e.getMessage();
        }

        request.setAttribute(SessionAttributes.MESSAGE, message);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
