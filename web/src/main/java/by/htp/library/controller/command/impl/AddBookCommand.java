package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.controller.command.Command;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;

public class AddBookCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();
		
		String authorSurname = request.getParameter("surname");
		String authorName = request.getParameter("name");
		Author author = new Author(authorSurname, authorName);
		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String status = "available";
		
		ArrayList<Author> authors = new ArrayList<>();
		authors.add(author);
		
		Book book = null;
		
		String goToPage = "";
		String message = "";
		String url = request.getRequestURL().toString();
		
		try {
			book = new Book(authors, title, status, genre);
			bookService.addBook(book);
			message = "Book successfully added!";
		} catch (ServiceException e) {
			e.printStackTrace();
			message = "Book not added: " + e.getMessage();
		}
		
		goToPage = "/WEB-INF/jsp/account/admin/AddingBook.jsp";
		request.getSession().setAttribute("goToPage", goToPage);
		url = url + "?command=goToPageForLogUser";
		request.getSession().setAttribute("url", url);
		request.setAttribute("message", message);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
	}
	
}
