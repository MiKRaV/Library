package by.htp.library.service.helper;

public enum ServiceMessages {
    INCORRECT_LOGIN("Incorrect login"),
    INCORRECT_PASSWORD("Incorrect password"),
    INCORRECT_NAME("Incorrect name"),
    INCORRECT_SURNAME("Incorrect surname"),
    INCORRECT_EMAIL("Incorrect e-mail"),
    REGISTRATION_FAILED("Registration failed"),
    LOGINATION_FAILED("Logination failed"),
    USERS_LIST_NOT_RECEIVED("List of users not received");

    private String message;

    ServiceMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
