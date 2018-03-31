package by.htp.library.controller.command.impl;

import by.htp.library.controller.FrontController;
import by.htp.library.controller.command.Command;
import by.htp.library.controller.helper.CommandName;
import by.htp.library.controller.helper.RequestParameters;
import by.htp.library.controller.helper.SessionAttributes;
import by.htp.library.controller.helper.WebHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String goToPage = request.getParameter(RequestParameters.GO_TO_PAGE);

        //String url = WebHelper.urlGenerator(request, CommandName.GO_TO_PAGE);

        //request.getSession().setAttribute(SessionAttributes.URL, url);

        //response.sendRedirect(goToPage);

        RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
        dispatcher.forward(request, response);
    }
}
