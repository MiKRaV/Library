package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.FrontController;
import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.RequestParameters;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;

public class ChangeLocalCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession(true).setAttribute(SessionAttributes.LOCAL,
				request.getParameter(RequestParameters.LOCAL));
		
		String prevUrl = (String) request.getSession().getAttribute(SessionAttributes.URL);
		
		/*
		if(!FrontController.isAttributeExecute(request, SessionAttributes.URL)) {
			prevUrl = WebHelper.START_URL;
			request.getSession(true).setAttribute(SessionAttributes.URL, prevUrl);
		} else {
			prevUrl = request.getSession().getAttribute(SessionAttributes.URL).toString();
		}
		*/
	
		//response.sendRedirect(prevUrl);

		RequestDispatcher dispatcher = request.getRequestDispatcher(prevUrl);
		dispatcher.forward(request, response);
	}

}
