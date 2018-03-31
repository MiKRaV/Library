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

        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        int orderID = Integer.parseInt(request.getParameter(RequestParameters.ORDER_ID));
        String orderStatus = request.getParameter(RequestParameters.ORDER_STATUS);
        List<Order> orderList = null;
        String goToPage = "";
        String url = "";
        int pageSize = 10;
        int page = 1;
        long orderCount;
        int pageCount;

        try {
            if (request.getAttribute(RequestAttributes.CURRENT_PAGE) != null)
                page = (int) request.getAttribute(RequestAttributes.CURRENT_PAGE);
            else if (request.getParameter(RequestParameters.PAGE) != null)
                page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));

            if (request.getParameter(RequestParameters.PAGE_SIZE) != null)
                pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));

            orderService.changeOrderStatus(orderID, orderStatus);

            orderList = orderService.getAllOrders(user, page, pageSize);

            orderCount = orderService.getTotalOrderCount();
            pageCount = (int) Math.ceil((orderCount * 1.0 ) / pageSize);

            request.getSession().setAttribute(SessionAttributes.PAGE_COUNT, pageCount);
            request.getSession().setAttribute(SessionAttributes.CURRENT_PAGE, page);
            request.getSession().setAttribute(SessionAttributes.ORDER_LIST, orderList);
            request.getSession().setAttribute(SessionAttributes.PAGE_SIZE, pageSize);
        } catch (ServiceException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
        }

        goToPage = WebHelper.pageGenerator(Pages.ORDERS);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
