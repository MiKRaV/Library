package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class RegistrationCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("e-mail");
		String userType = request.getParameter("userType");
		int countBook = 0;
		String userStatus = "active";
		
		//System.out.println(login);
		//System.out.println(password);
		//System.out.println(name);
		//System.out.println(surname);
		//System.out.println(email);
		//System.out.println(userType);
		
		String goToPage = "";
		String errorMessage = "";
		String url = request.getRequestURL().toString();
		User user = null;
		
		try {
			user = new User(login, password, name, surname, email, userType, countBook, userStatus);
			userService.registration(user);
			goToPage = "/WEB-INF/jsp/account/RegistrationMessage.jsp";
			request.getSession().setAttribute("goToPage", goToPage);
			request.getSession().setAttribute("user", user);
			url = url + "?command=goToPageForLogUser";
			request.getSession().setAttribute("url", url);
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = "/WEB-INF/jsp/registration.jsp";
			url = url + "?command=startRegistration";
			errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		
		System.out.println(user);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}

}
