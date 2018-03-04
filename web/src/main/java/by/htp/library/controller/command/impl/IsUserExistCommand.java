package by.htp.library.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.command.Command;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;
import by.htp.library.service.UserService;

public class IsUserExistCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	//—“¿–€… Ã≈“Œƒ
	public String execute(String request) {
		
		String[] params = request.split("\\s+");
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();
		String login = params[1];
		String response = null;
		
		try {
			if(userService.isUserExist(login)) {
				response = "User with login \"" + login + "\" is exist.";
			} else {
				response = "User with login \"" + login + "\" is not exist.";
			}
		} catch (ServiceException e) {
			response = "Sorry, ....";
		}
		
		return response;
	}

	

}
