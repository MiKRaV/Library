package by.htp.library.controller.command.impl;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.Pages;
import by.htp.library.controller.helper.RequestParameters;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;
import by.htp.library.entity.Order;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetOrderInfoCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();

        int orderID = Integer.parseInt(request.getParameter(RequestParameters.ORDER_ID));
        Order order = null;
        String message = "";
        String goToPage = "";

        try {
            order = orderService.getOrder(orderID);
            request.getSession().setAttribute(SessionAttributes.ORDER, order);
        } catch (ServiceException e) {
            message = e.getMessage();
            request.getSession().setAttribute(SessionAttributes.MESSAGE, message);
        }

        goToPage = WebHelper.pageGenerator(Pages.ORDER_INFO);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
