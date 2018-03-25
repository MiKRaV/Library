package by.htp.library.controller.helper;

public enum CommandName {
	START_APP_LOGINATION("startAppLogination"),
	START_APP_REGISTARTION("startAppRegistration"),
	GO_TO_PAGE_FOR_LOG_USER("goToPageForLogUser"),
	GET_ALL_USERS("getAllUsers"),
	CHANGE_LOCAL("changeLocal"),
	LOGINATION("logination"),
	REGISTRATION("registration"),
    GO_TO_USER_DATA_PAGE("goToUserDataPage"),
	CHANGE_USER_DATA("changeUserData"),
	GO_TO_USER_REMOVE_PAGE("goToUserRemovePage"),
    GO_TO_SEARCH_PAGE("goToSearchUserPage"),
	ADD_BOOK("addBook"),
	FIND_USER("findUser"),
	BLOCK_UNLOCK_USER("blockUnlockUser"),
	GO_TO_ADDING_BOOK_PAGE("goToAddingBookPage"),
	REMOVE_USER("removeUser"),
	GO_TO_START_PAGE("goToStartPage"),
	LOG_OUT("logOutCommand"),
	GO_TO_ACCOUNT("goToAccount"),
	SEARCH_BY_TITLE("searchByTitle"),
	GET_ALL_BOOKS("getAllBooks");
	
	private String commandName;

	CommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
