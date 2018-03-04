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

public class BlockUnlockUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User foundUser = (User) request.getSession().getAttribute("foundUser");
		String login = foundUser.getLogin();
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = "";
		String errorMessage = "";
		String url = request.getRequestURL().toString();
		
		try {
			userService.blockUnlockUser(login);
			foundUser = userService.findUserByLogin(login);
			request.getSession().setAttribute("foundUser", foundUser); 
		} catch (ServiceException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
		}
		
		goToPage = "/WEB-INF/jsp/account/admin/FoundUserDataPage.jsp";
		url = url + "?command=goToPageForLogUser";
		
		request.getSession().setAttribute("goToPage", goToPage);
		request.getSession().setAttribute("url", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}

}
