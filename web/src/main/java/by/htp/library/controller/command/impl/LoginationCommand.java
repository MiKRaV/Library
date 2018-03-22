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

public class LoginationCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(RequestParameters.USER_LOGIN);
		String password = request.getParameter(RequestParameters.USER_PASSWORD);
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		User user = null;
		
		String goToPage = "";
		String errorMessage = "";
		String url = "";
		
		try {
			user = userService.logination(login, password);
			System.out.println(user);
			if(user != null) {
				goToPage = WebHelper.pageGenerator(Pages.LOGINATION_MESSAGE);
				url = WebHelper.urlGenerator(request, CommandName.GO_TO_PAGE_FOR_LOG_USER);
				request.getSession().setAttribute(SessionAttributes.USER, user);
			} else {
				goToPage = WebHelper.pageGenerator(Pages.LOGINATION);
				url = WebHelper.urlGenerator(request, CommandName.START_APP_LOGINATION);
				errorMessage = "Try it again"; //set constant
				request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
			}
		} catch (ServiceException e) {
			// logging
			e.printStackTrace(); //stub
			goToPage = WebHelper.pageGenerator(Pages.LOGINATION);
			url = WebHelper.urlGenerator(request, CommandName.START_APP_LOGINATION);
			errorMessage = e.getMessage();
			request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
		}
		
		request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		request.getSession().setAttribute(SessionAttributes.URL, url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
