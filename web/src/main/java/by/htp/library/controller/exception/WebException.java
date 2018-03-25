package by.htp.library.controller.exception;

public class WebException extends Exception {

    public WebException() {super();}

    public WebException(String message) {super(message);}

    public WebException(Exception e) {super(e);}

    public WebException(String message, Exception e) {super(message, e);}
}
