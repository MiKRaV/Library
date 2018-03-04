package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;

public class StartAppCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String goToPage = "";
		String url = request.getRequestURL().toString();
		
		if(request.getParameter("command").equals("startAppLogination")) {
			goToPage = "/WEB-INF/jsp/logination.jsp";
			url = url + "?command=startAppLogination";
		} else if(request.getParameter("command").equals("startAppRegistration")) {
			goToPage = "/WEB-INF/jsp/registration.jsp";
			url = url + "?command=startAppRegistration";
		}
		
		request.getSession().setAttribute("url", url);
		
		//System.out.println(url);
		//System.out.println("goToPage: " + goToPage);
		
		//System.out.println("ContextPath: " + request.getContextPath());
		//System.out.println("RequestURI: " + request.getRequestURI());
		//System.out.println("RequestURL: " + request.getRequestURL());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}

}
