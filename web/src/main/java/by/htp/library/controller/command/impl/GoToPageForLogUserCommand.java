package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.FrontController;
import by.htp.library.controller.command.Command;

public class GoToPageForLogUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String goToPage = "";
		
		if(FrontController.isAttributeExecute(request, "user")) {
			goToPage = request.getSession().getAttribute("goToPage").toString();
		} else {
			goToPage = "/WEB-INF/jsp/errors/UserIsNotLoggedInError.jsp";
		}
		
		String url = request.getRequestURL().toString();
		url = url + "?command=goToPageForLogUser";
		
		request.getSession().setAttribute("url", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
