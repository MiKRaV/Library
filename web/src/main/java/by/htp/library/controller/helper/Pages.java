package by.htp.library.controller.helper;

public enum Pages {
    LOGINATION("logination.jsp"),
    LOGINATION_MESSAGE("account/LoginationMessage.jsp"),
    REGISTRATION("registration.jsp"),
    REGISTRATION_MESSAGE("account/RegistrationMessage.jsp"),
    TABLE_WITH_USERS("account/admin/TableWithUsers.jsp"),
    ADMIN_MAIN_PAGE("account/admin/AdminMainPage.jsp"),
    USER_DATA("account/UserDataPage.jsp"),
    REMOVE_USER_MESSAGE("account/admin/RemoveUserMessagePage.jsp"),
    USER_REMOVE("account/admin/UserRemovePage.jsp"),
    FOUND_USER_DATA("account/admin/FoundUserDataPage.jsp"),
    SEARCH_USER("account/admin/SearchUserPage.jsp"),
    ADDING_BOOK("account/admin/AddingBook.jsp"),
    START("index.jsp"),
    READER_MAIN_PAGE("account/reader/ReaderMainPage.jsp"),
    TABLE_WITH_BOOKS("account/TableWithBooks.jsp"),
    BASKET("account/reader/Basket.jsp"),
    ORDERS("account/Orders.jsp"),
    ORDER_INFO("account/OrderInfo.jsp");

    private String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
