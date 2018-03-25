package by.htp.library.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.exception.WebException;
import by.htp.library.controller.helper.*;
import by.htp.library.entity.User;
import by.htp.library.controller.command.Command;
import by.htp.library.entity.helper.UserParameters;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class ChangeUserDataCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		
		String goToPage = WebHelper.pageGenerator(Pages.USER_DATA);
		String url = WebHelper.urlGenerator(request, CommandName.GO_TO_USER_DATA_PAGE);
		String userParameter = request.getParameter(RequestParameters.USER_PARAMETER);
		String message = "";
		UserParameters parameter = null;
		String newValue = null;

		try {
			switch (userParameter) {
				case RequestParameters.USER_PASSWORD:
					String oldPassword = request.getParameter(RequestParameters.USER_OLD_PASSWORD);
					String newPassword = request.getParameter(RequestParameters.USER_NEW_PASSWORD);
					String confirmPassword = request.getParameter(RequestParameters.USER_CONFIRM_PASSWORD);
					if(!oldPassword.equals(user.getPassword())) {
						throw new WebException(Messages.INVALID_PASSWORD);
					} else if (!newPassword.equals(confirmPassword)) {
						throw new WebException(Messages.PASSWORDS_DO_NOT_MATCH);
					}
					parameter = UserParameters.PASSWORD;
					newValue = newPassword;
					break;
				case RequestParameters.USER_NAME:
					parameter = UserParameters.NAME;
					newValue = request.getParameter(RequestParameters.USER_NEW_NAME);
					break;
				case RequestParameters.USER_SURNAME:
					parameter = UserParameters.SURNAME;
					newValue = request.getParameter(RequestParameters.USER_NEW_SURNAME);
					break;
				case RequestParameters.USER_EMAIL:
					parameter = UserParameters.EMAIL;
					newValue = request.getParameter(RequestParameters.USER_NEW_EMAIL);
					break;
			}
			user = userService.changeUserData(user, parameter, newValue);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = parameter.getParameter().toUpperCase() + " changed successfully!";
		} catch (ServiceException e) {
			e.printStackTrace();
			message = e.getMessage();
		} catch (WebException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		
		request.getSession().setAttribute(SessionAttributes.URL, url);
		request.setAttribute(SessionAttributes.MESSAGE, message);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}
}
