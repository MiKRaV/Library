package by.htp.library.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.library.controller.helper.*;
import by.htp.library.entity.Book;
import by.htp.library.controller.command.Command;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.ServiceFactory;

public class GetAllBooksCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Book> bookList = null;
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		BookService bookService = serviceFactory.getBookService();

		String goToPage = "";
		//String url = "";
		int pageSize = 1;
		int page = 1;
		long bookCount;
		int pageCount;
		
		try {
			if (request.getAttribute(RequestAttributes.CURRENT_PAGE) != null)
				page = (int) request.getAttribute(RequestAttributes.CURRENT_PAGE);
			else if (request.getParameter(RequestParameters.PAGE) != null)
				page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));

			if (request.getParameter(RequestParameters.PAGE_SIZE) != null)
				pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));

			bookList = bookService.getAllBooks(page, pageSize);

			bookCount = bookService.getBookCount(); //total book count
			pageCount = (int) Math.ceil((bookCount * 1.0 ) / pageSize);

			request.getSession().setAttribute(SessionAttributes.PAGE_COUNT, pageCount);
			request.getSession().setAttribute(SessionAttributes.CURRENT_PAGE, page);
			request.getSession().setAttribute(SessionAttributes.BOOK_LIST, bookList);
			request.getSession().setAttribute(SessionAttributes.PAGE_SIZE, pageSize);

			goToPage = WebHelper.pageGenerator(Pages.TABLE_WITH_BOOKS);
			//url = WebHelper.urlGenerator(request, CommandName.GET_ALL_BOOKS);
			//request.getSession().setAttribute(SessionAttributes.URL, url);
			request.getSession().setAttribute(SessionAttributes.GO_TO_PAGE, goToPage);
		} catch (ServiceException e) {
			e.printStackTrace();
			goToPage = WebHelper.pageGenerator(Pages.TABLE_WITH_BOOKS);
			String errorMessage = e.getMessage();
			request.setAttribute(RequestAttributes.ERROR_MESSAGE, errorMessage);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}
}
