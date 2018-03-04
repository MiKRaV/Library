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

public class LoginationCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		User user = null;
		
		String goToPage = "";
		String errorMessage = "";
		String url = request.getRequestURL().toString();
		
		try {
			user = userService.logination(login, password);
			System.out.println(user);
			if(user != null) {
				goToPage = "/WEB-INF/jsp/account/LoginationMessage.jsp";
				url = url + "?command=goToPageForLogUser";
				request.getSession().setAttribute("user", user);
			} else {
				goToPage = "/WEB-INF/jsp/logination.jsp";
				url = url + "?command=startAppLogination";
				errorMessage = "Try it again";
				request.setAttribute("errorMessage", errorMessage);
			}
		} catch (ServiceException e) {
			// logging
			e.printStackTrace(); //stub
			goToPage = "/WEB-INF/jsp/logination.jsp";
			url = url + "?command=startAppLogination";
			errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		
		request.getSession().setAttribute("goToPage", goToPage);
		request.getSession().setAttribute("url", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
