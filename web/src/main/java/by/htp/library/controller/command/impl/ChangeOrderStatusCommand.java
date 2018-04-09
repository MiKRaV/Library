package by.htp.library.controller.command.impl;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.*;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChangeOrderStatusCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        OrderService orderService = serviceFactory.getOrderService();

        int orderID = Integer.parseInt(request.getParameter(RequestParameters.ORDER_ID));
        String orderStatus = request.getParameter(RequestParameters.ORDER_STATUS);
        Order order = null;
        String goToPage = "";

        try {
            orderService.changeOrderStatus(orderID, orderStatus);
            order = orderService.getOrder(orderID);
            request.setAttribute(RequestAttributes.ORDER, order);
        } catch (ServiceException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
        }

        goToPage = WebHelper.pageGenerator(Pages.ORDER_INFO);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
