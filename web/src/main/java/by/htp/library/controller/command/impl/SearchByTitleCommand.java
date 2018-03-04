package by.htp.library.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.Book;
import by.htp.library.controller.command.Command;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;

public class SearchByTitleCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	//������ �����!!!
	public String execute(String request) {
		
		String[] params = request.split("\\s+");
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();
		
		String title = params[1];
		List<Book> books;
		
		String response = null;
		
		try {
			response = "Books with title \"" + title + "\":";
			books = bookService.searchByTitle(title);
			for(Book x : books) {
				response = response.concat("\n[" + x.getAuthors() + " - \"" + x.getTitle() + "\"]");
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	

}
