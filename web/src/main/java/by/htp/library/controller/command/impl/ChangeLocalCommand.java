package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.FrontController;
import by.htp.library.controller.command.Command;

public class ChangeLocalCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession(true).setAttribute("local", request.getParameter("local"));
		
		String prevUrl = "";
		
		if(!FrontController.isAttributeExecute(request, "url")) {
			prevUrl = "http://localhost:8080/LybraryWeb/";
			request.getSession(true).setAttribute("url", prevUrl);
		} else {
			prevUrl = request.getSession().getAttribute("url").toString(); 
		}
	
		response.sendRedirect(prevUrl);
	}

}
