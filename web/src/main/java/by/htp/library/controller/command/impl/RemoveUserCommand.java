package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class RemoveUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = "";
		String url = request.getRequestURL().toString();
		
		try {
			userService.removeUser(login);
			goToPage = "/WEB-INF/jsp/account/admin/RemoveUserMessagePage.jsp";
			url = url + "?command=goToPageForLogUser";
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = "/WEB-INF/jsp/account/admin/UserRemovePage.jsp";
			url = url + "?command=goToUserRemovePage";
			String errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		
		request.getSession().setAttribute("goToPage", goToPage);
		request.getSession().setAttribute("url", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
