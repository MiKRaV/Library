package by.htp.library.controller.helper;

public enum Pages {
    LOGINATION("logination.jsp"),
    LOGINATION_MESSAGE("account/LoginationMessage.jsp"),
    REGISTRATION("registration.jsp"),
    REGISTRATION_MESSAGE("account/RegistrationMessage.jsp"),
    TABLE_WITH_USERS("account/admin/TableWithUsers.jsp"),
    ADMIN_MAIN_PAGE("account/admin/AdminMainPage.jsp");

    private String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
