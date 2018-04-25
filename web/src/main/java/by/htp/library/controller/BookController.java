package by.htp.library.controller;

import by.htp.library.controller.helper.*;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.entity.Note;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.BookStatus;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/books-list", method = RequestMethod.GET)
    public String getAllBooks(HttpServletRequest request, ModelMap model) {
        List<Book> bookList = null;
        int pageSize = 10;
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

            model.addAttribute("pageCount", pageCount);
            model.addAttribute("currentPage", page);
            model.addAttribute("bookList", bookList);
            model.addAttribute("pageSize", pageSize);
        } catch (ServiceException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }
        return Pages.BOOKS;
    }

    @RequestMapping(value = "/books-list", method = RequestMethod.POST)
    public String addBookToBasket(HttpServletRequest request, ModelMap model) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);

        List<Book> bookList = (List<Book>) request.getSession().getAttribute(SessionAttributes.BOOK_LIST);
        int bookID = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));
        int page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
        int pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));
        int pageCount = Integer.parseInt(request.getParameter(RequestParameters.PAGE_COUNT));

        String message = "";

        try {
            user = userService.addBookToBasket(user, bookID);
            request.getSession().setAttribute(SessionAttributes.USER, user);
            message = Messages.BOOK_ADDED_TO_BASKET;
        } catch (ServiceException e) {
            e.printStackTrace();
            message = Messages.BOOK_NOT_ADDED_TO_BASKET + " : " + e.getMessage();
        }

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("bookList", bookList);
        model.addAttribute("pageSize", pageSize);

        request.setAttribute(SessionAttributes.MESSAGE, message);
        return Pages.BOOKS;
    }

    @RequestMapping(value = "/adding-book", method = RequestMethod.GET)
    public String getAddingBookPage() {
        return Pages.ADDING_BOOK;
    }

    @RequestMapping(value = "/adding-book", method = RequestMethod.POST)
    public String addBook(HttpServletRequest request, ModelMap model) {
        List<Book> books = new ArrayList<>();
        String title = request.getParameter(RequestParameters.BOOK_TITLE);
        String genre = request.getParameter(RequestParameters.BOOK_GENRE);
        List<Note> register = new ArrayList<>();
        Book book = new Book(null,  title, genre, BookStatus.AVAILABLE, null, null, null, register);
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

        model.addAttribute("message", message);

        return Pages.ADDING_BOOK;
    }
}
