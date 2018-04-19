package by.htp.library.controller;

import by.htp.library.controller.helper.*;
import by.htp.library.entity.Book;
import by.htp.library.entity.Order;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.OrderStatus;
import by.htp.library.service.OrderService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/orders-list", method = RequestMethod.GET)
    public String getAllOrders(HttpServletRequest request, ModelMap model) {
        List<Order> orderList = null;

        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
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

            orderList = orderService.getAllOrders(user, page, pageSize);

            orderCount = orderService.getTotalOrderCount();
            pageCount = (int) Math.ceil((orderCount * 1.0 ) / pageSize);

            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPage", page);
            model.addAttribute("orderList", orderList);
            model.addAttribute("pageSize", pageSize);
        } catch (ServiceException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }

        return "account/Orders";
    }

    @RequestMapping(value = "/order-info", method = RequestMethod.GET)
    public String getOrderInfo(HttpServletRequest request, ModelMap model) {
        List<Order> orderList = null;
        Order order = null;

        int orderID = 0;
        if (request.getParameter(RequestParameters.ORDER_ID) != null) {
            orderID = Integer.parseInt(request.getParameter(RequestParameters.ORDER_ID));
        } else {
            order = (Order) request.getSession().getAttribute(SessionAttributes.ORDER);
            orderID = order.getId();
        }

        String message = "";

        try {
            order = orderService.getOrder(orderID);
            request.getSession().setAttribute(SessionAttributes.ORDER, order);
            //model.addAttribute("order", order);
        } catch (ServiceException e) {
            message = e.getMessage();
            model.addAttribute("message", message);
        }

        return "account/OrderInfo";
    }

    @RequestMapping(value = "/order-info", method = RequestMethod.POST)
    public String changeOrderStatus(HttpServletRequest request, ModelMap model) {
        int orderID = Integer.parseInt(request.getParameter(RequestParameters.ORDER_ID));
        String orderStatus = request.getParameter(RequestParameters.ORDER_STATUS);
        Order order = null;

        try {
            orderService.changeOrderStatus(orderID, orderStatus);
            order = orderService.getOrder(orderID);
            request.setAttribute(RequestAttributes.ORDER, order);
        } catch (ServiceException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
        }

        return "account/OrderInfo";
    }

    @RequestMapping(value = "/new-order", method = RequestMethod.POST)
    public String createOrder(HttpServletRequest request, ModelMap model) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        List<Book> basket = user.getBasket();
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

        model.addAttribute("message", message);

        return "account/reader/Basket";
    }
}
