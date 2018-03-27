package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.helper.*;
import by.htp.library.entity.Book;
import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.entity.UserData;
import by.htp.library.entity.helper.UserHelper;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class RegistrationCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String login = request.getParameter(RequestParameters.USER_LOGIN);
		String password = request.getParameter(RequestParameters.USER_PASSWORD);
		String name = request.getParameter(RequestParameters.USER_NAME);
		String surname = request.getParameter(RequestParameters.USER_SURNAME);
		String email = request.getParameter(RequestParameters.USER_EMAIL);
		String userType = request.getParameter(RequestParameters.USER_TYPE);
		String userStatus = UserHelper.STATUS_ACTIVE;
		List<Book> basket = new ArrayList<>();
		
		String goToPage = "";
		String url;
		User user = null;
		
		try {
			user = new User(null, login, password, userType, userStatus, null, basket);
			UserData userData = new UserData(null, name, surname, email, user);
			user.setUserData(userData);
			userService.registration(user);
			goToPage = WebHelper.pageGenerator(Pages.REGISTRATION_MESSAGE);
			request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
			request.getSession().setAttribute(SessionAttributes.USER, user);
			url = WebHelper.urlGenerator(request, CommandName.GO_TO_PAGE_FOR_LOG_USER);
			request.getSession().setAttribute(SessionAttributes.URL, url);
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = WebHelper.pageGenerator(Pages.REGISTRATION);
			url = WebHelper.urlGenerator(request, CommandName.START_APP_REGISTARTION);
			request.setAttribute(RequestAttributes.ERROR_MESSAGE, e.getMessage());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
