package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.entity.helper.UserParameters;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class ChangeUserDataCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute("user");
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = "/WEB-INF/jsp/account/UserDataPage.jsp";
		String url = request.getRequestURL().toString();
		url = url + "?command=goToUserDataPage";
		String parameter = request.getParameter("parameter");
		String message = "";
		
		switch(parameter) {
		case "password":
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			if(!oldPassword.equals(user.getPassword())) {
				message = "Invalid password";
				break;
			} else if (!newPassword.equals(confirmPassword)) {
				message = "Passwords do not match";
				break;
			}
			try {
				userService.changeUserData(user, UserParameters.PASSWORD, newPassword);
				user = userService.logination(user.getLogin(), newPassword);
				request.getSession().setAttribute("user", user);
				message = "Password changed successfully!";
			} catch (ServiceException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			
			break;
		case "name":
			String newName = request.getParameter("newName");
			try {
				userService.changeUserData(user, UserParameters.NAME, newName);
				user = userService.logination(user.getLogin(), user.getPassword());
				request.getSession().setAttribute("user", user);
				message = "Name changed successfully!";
			} catch (ServiceException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			break;
		case "surname":
			String newSurname = request.getParameter("newSurname");
			try {
				userService.changeUserData(user, UserParameters.SURNAME, newSurname);
				user = userService.logination(user.getLogin(), user.getPassword());
				request.getSession().setAttribute("user", user);
				message = "Surname changed successfully!";
			} catch (ServiceException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			break;
		case "email":
			String newEmail = request.getParameter("newEmail");
			try {
				userService.changeUserData(user, UserParameters.EMAIL, newEmail);
				user = userService.logination(user.getLogin(), user.getPassword());
				request.getSession().setAttribute("user", user);
				message = "E-mail changed successfully!";
			} catch (ServiceException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
			break;
		}
		
		//String name = request.getParameter("name");
		//String surname = request.getParameter("surname");
		//String email = request.getParameter("e-mail");
		
		request.getSession().setAttribute("url", url);
		request.setAttribute("message", message);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}
	
	//������ �����
	/*
	public String execute(String request) {
		
		String[] params = request.split("\\s+");
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		String login = params[1];
		String password = params[2];
		String data = params[3];
		String dataValue= params[4];
		String response = null;
	
		try {
			User user = userService.logination(login, password);
			
			userService.changeUserData(user, data, dataValue);
			response = "Data of user \"" + login + "\" changed successfully";
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		return response;
	}
	*/

	

}
