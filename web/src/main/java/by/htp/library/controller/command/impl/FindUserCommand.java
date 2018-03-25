package by.htp.library.controller.command.impl;

import java.io.IOException;

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

public class FindUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(RequestParameters.USER_LOGIN);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = "";
		String errorMessage = "";
		String url = "";
		
		User foundUser = null;
		
		try {
			foundUser = userService.findUserByLogin(login);
			goToPage = WebHelper.pageGenerator(Pages.FOUND_USER_DATA);
			url = WebHelper.urlGenerator(request, CommandName.GO_TO_PAGE_FOR_LOG_USER);
			request.getSession().setAttribute(SessionAttributes.FOUND_USER, foundUser);
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = WebHelper.pageGenerator(Pages.SEARCH_USER);
			url = WebHelper.urlGenerator(request, CommandName.GO_TO_SEARCH_PAGE);
			errorMessage = e.getMessage();
			request.setAttribute(SessionAttributes.ERROR_MESSAGE, errorMessage);
		}
		
		request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		request.getSession().setAttribute(SessionAttributes.URL, url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
