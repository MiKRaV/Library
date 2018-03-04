package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;

public class GoToAccountCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute("user");
		String userType = user.getUserType();
		
		String goToPage = "";
		
		if(userType.equals("reader")) {
			goToPage = "/WEB-INF/jsp/account/reader/ReaderMainPage.jsp";
			request.getSession().setAttribute("goToPage", goToPage);
		} else if(userType.equals("admin")) {
			goToPage = "/WEB-INF/jsp/account/admin/AdminMainPage.jsp";
			request.getSession().setAttribute("goToPage", goToPage);
		}	
		
		String url = request.getRequestURL().toString();
		url = url + "?command=goToAccount";
		
		request.getSession().setAttribute("url", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
