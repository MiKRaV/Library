package by.htp.library.controller.helper;

import javax.servlet.http.HttpServletRequest;

public class WebHelper {

    public static final String START_URL = "http://localhost:8080/LibraryWeb/";

    public static String urlGenerator(HttpServletRequest request, CommandName commandName) {
        return request.getRequestURL().toString() + "?command=" + commandName.getCommandName();
    }

    public static String pageGenerator(Pages page) {
        return "/WEB-INF/jsp/" + page.getPage();
    }

}
