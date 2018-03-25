package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.helper.CommandName;
import by.htp.library.controller.helper.Pages;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;
import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.entity.helper.UserHelper;

public class GoToAccountCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
		String userType = user.getType();
		
		String goToPage = "";
		
		if(userType.equals(UserHelper.TYPE_READER)) {
			goToPage = WebHelper.pageGenerator(Pages.READER_MAIN_PAGE);
		} else if(userType.equals(UserHelper.TYPE_ADMIN)) {
			goToPage = WebHelper.pageGenerator(Pages.ADMIN_MAIN_PAGE);
		}

		request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		
		String url = WebHelper.urlGenerator(request, CommandName.GO_TO_ACCOUNT);
		request.getSession().setAttribute(SessionAttributes.URL, url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
