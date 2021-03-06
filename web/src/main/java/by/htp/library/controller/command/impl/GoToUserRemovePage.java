package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;

public class GoToUserRemovePage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String goToPage = "";
		
		goToPage = "/WEB-INF/jsp/account/admin/UserRemovePage.jsp";
		request.getSession().setAttribute("goToPage", goToPage);
		
		String url = request.getRequestURL().toString();
		url = url + "?command=goToUserRemovePage";
		
		request.getSession().setAttribute("url", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}

}
