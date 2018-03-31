package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.helper.*;
import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class GetAllUsersCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> userList = null;

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		String goToPage = "";
		String url = "";
		int pageSize = 1;
		int page = 1;
		long userCount;
		int pageCount;
		
		try {
			if (request.getParameter(RequestParameters.PAGE) != null)
				page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));

			if (request.getParameter(RequestParameters.PAGE_SIZE) != null)
				pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));

			userCount = userService.getUserCount();
			pageCount = (int) Math.ceil(userCount / pageSize);

			userList = userService.getAllUsersList(page, pageSize);

			goToPage = WebHelper.pageGenerator(Pages.TABLE_WITH_USERS);
			//url = WebHelper.urlGenerator(request, CommandName.GET_ALL_USERS);

			request.getSession().setAttribute(SessionAttributes.PAGE_COUNT, pageCount);
			request.getSession().setAttribute(SessionAttributes.CURRENT_PAGE, page);
			request.getSession().setAttribute(SessionAttributes.USER_LIST, userList);
			//request.getSession().setAttribute(SessionAttributes.URL, url);
			request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = WebHelper.pageGenerator(Pages.ADMIN_MAIN_PAGE);
			String errorMessage = e.getMessage();
			request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
