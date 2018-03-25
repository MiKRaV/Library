package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.CommandName;
import by.htp.library.controller.helper.Pages;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;

public class GoToAddingBookPage implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String goToPage = "";
		
		goToPage = WebHelper.pageGenerator(Pages.ADDING_BOOK);
		request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		
		String url = "";
		url = WebHelper.urlGenerator(request, CommandName.GO_TO_ADDING_BOOK_PAGE);
		
		request.getSession().setAttribute(SessionAttributes.URL, url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
