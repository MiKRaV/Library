package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class GetAllUsersCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<User> userList = null;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = "";
		String url = request.getRequestURL().toString();
		
		try {
			userList = userService.getAllUsersList();
			goToPage = "/WEB-INF/jsp/account/admin/TableWithUsers.jsp";	
			url = url + "?command=getAllUsers";
			request.getSession().setAttribute("url", url);
			request.getSession().setAttribute("goToPage", goToPage);
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = "/WEB-INF/jsp/account/admin/AdminMainPage.jsp";
			url = url + "?command=goToAccount";
			String errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		request.setAttribute("userList", userList);
		dispatcher.forward(request, response);
	}
}
