package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.Book;
import by.htp.library.controller.command.Command;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;

public class GetAllBooksCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Book> bookList = null;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();
		
		String goToPage = "";
		
		try {
			bookList = (ArrayList<Book>) bookService.getAllBooks();
			goToPage = "/WEB-INF/jsp/account/reader/TableWithAllBooks.jsp";	
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = "error.jsp";
		}
		
		String url = request.getRequestURL().toString();
		url = url + "?command=getAllBooks";
		
		request.getSession().setAttribute("url", url);
		request.getSession().setAttribute("goToPage", goToPage);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		request.setAttribute("bookList", bookList);
		dispatcher.forward(request, response);
		
	}

}
