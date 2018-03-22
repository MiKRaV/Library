package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.*;

public class StartAppCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String goToPage = "";
		String url = "";
		String command = request.getParameter(RequestParameters.COMMAND);

		if (command.equals(CommandName.START_APP_LOGINATION.getCommandName())) {
			goToPage = WebHelper.pageGenerator(Pages.LOGINATION);
			url = WebHelper.urlGenerator(request, CommandName.START_APP_LOGINATION);
		} else if (command.equals(CommandName.START_APP_REGISTARTION.getCommandName())) {
			goToPage = WebHelper.pageGenerator(Pages.REGISTRATION);
			url = WebHelper.urlGenerator(request, CommandName.START_APP_REGISTARTION);
		}
		
		request.getSession().setAttribute(SessionAttributes.URL, url);
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
