package by.htp.library.controller.helper;

public enum CommandName {
	START_APP_LOGINATION("startAppLogination"),
	START_APP_REGISTARTION("startAppRegistration"),
	GO_TO_PAGE_FOR_LOG_USER("goToPageForLogUser"),
	GET_ALL_USERS("getAllUsers");
	
	private String commandName;

	CommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
