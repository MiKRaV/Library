package by.htp.library.controller;

import by.htp.library.controller.helper.*;
import by.htp.library.entity.Author;
import by.htp.library.entity.Book;
import by.htp.library.entity.Note;
import by.htp.library.entity.User;
import by.htp.library.entity.helper.BookStatus;
import by.htp.library.entity.helper.Genre;
import by.htp.library.service.BookService;
import by.htp.library.service.ServiceException;
import by.htp.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        String stringGenre = request.getParameter(RequestParameters.BOOK_GENRE);
        Genre genre = null;
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

            if (stringGenre != null && !stringGenre.equals("ALL")) {
                genre = Genre.valueOf(stringGenre);
                bookList = bookService.getBooksByGenre(genre, page, pageSize);
                bookCount = bookService.countBookByGenre(genre);
                model.addAttribute("bookGenre", stringGenre);
            } else {
                bookList = bookService.getAllBooks(page, pageSize);
                bookCount = bookService.getBookCount(); //total book count
            }


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
        String stringGenre = request.getParameter(RequestParameters.BOOK_GENRE);
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);
        //int bookID = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));
        //String message = "";


        List<Book> bookList = (List<Book>) request.getSession().getAttribute(SessionAttributes.BOOK_LIST);
        int bookID = Integer.parseInt(request.getParameter(RequestParameters.BOOK_ID));
        int page = Integer.parseInt(request.getParameter(RequestParameters.PAGE));
        int pageSize = Integer.parseInt(request.getParameter(RequestParameters.PAGE_SIZE));
        int pageCount = Integer.parseInt(request.getParameter(RequestParameters.PAGE_COUNT));

        String message = "";

        if (stringGenre != null) {
            model.addAttribute("bookGenre", stringGenre);
        }


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
        Genre genre = Genre.valueOf(request.getParameter(RequestParameters.BOOK_GENRE).toUpperCase());
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

    @RequestMapping(value = "/reader-books", method = RequestMethod.GET)
    public String readerBooks(HttpServletRequest request, ModelMap model) {
        User user = (User) request.getSession().getAttribute(SessionAttributes.USER);

        try {
            List<Note> subscription = userService.getSubscription(user);
            request.getSession().setAttribute("subscription", subscription);
        } catch (ServiceException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }
        return Pages.READER_BOOKS;
    }

    @RequestMapping(value = "/reader-books", method = RequestMethod.POST)
    public void returnBooks(HttpServletRequest request, ModelMap model) {
        int noteID = Integer.parseInt(request.getParameter("noteID"));
        int bookID = Integer.parseInt(request.getParameter("bookID"));

        try {
            bookService.returnBook(noteID, bookID);
        } catch (ServiceException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }
        readerBooks(request, model);
    }
}
