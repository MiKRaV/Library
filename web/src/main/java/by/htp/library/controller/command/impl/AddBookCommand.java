package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.helper.*;
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

		List<Book> books = new ArrayList<>();
		String title = request.getParameter(RequestParameters.BOOK_TITLE);
		String genre = request.getParameter(RequestParameters.BOOK_GENRE);
		Book book = new Book(null,  title, genre, null, null, null);
		books.add(book);

		List<Author> authors = new ArrayList<>();
		String[] authorsSurnames = request.getParameterValues(RequestParameters.AUTHORS_SURNAMES);
		String[] authorsNames = request.getParameterValues(RequestParameters.AUTHORS_NAMES);
		for (int i = 0; i < authorsNames.length; i++) {
			String authorSurname = authorsSurnames[i];
			String authorName = authorsNames[i];
			Author author = new Author(null, authorSurname, authorName, books);
			authors.add(author);
		}

		book.setAuthors(authors);

		String goToPage = "";
		String message = "";
		String url = "";
		
		try {
			bookService.addBook(book);
			message = Messages.BOOK_ADDED;
		} catch (ServiceException e) {
			e.printStackTrace();
			message = Messages.BOOK_NOT_ADDED + " : " + e.getMessage();
		}
		
		goToPage = WebHelper.pageGenerator(Pages.ADDING_BOOK);
		url = WebHelper.urlGenerator(request, CommandName.GO_TO_PAGE_FOR_LOG_USER);
		request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		request.getSession().setAttribute(SessionAttributes.URL, url);
		request.setAttribute(SessionAttributes.MESSAGE, message);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
