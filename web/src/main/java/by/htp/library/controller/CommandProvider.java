package by.htp.library.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.command.impl.*;
import by.htp.library.controller.helper.CommandName;

import static by.htp.library.controller.helper.CommandName.*;

public class CommandProvider {
	
	private Map<String, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(LOGINATION.getCommandName(), new LoginationCommand());
		commands.put(REGISTRATION.getCommandName(), new RegistrationCommand());
		commands.put(ADD_BOOK.getCommandName(), new AddBookCommand());
		commands.put(SEARCH_BY_TITLE.getCommandName(), new SearchByTitleCommand());
		commands.put(CHANGE_USER_DATA.getCommandName(), new ChangeUserDataCommand());
		commands.put(START_APP_LOGINATION.getCommandName(), new StartAppCommand());
		commands.put(START_APP_REGISTARTION.getCommandName(), new StartAppCommand());
		commands.put(GET_ALL_USERS.getCommandName(), new GetAllUsersCommand());
		commands.put(GET_ALL_BOOKS.getCommandName(), new GetAllBooksCommand());
		commands.put(CHANGE_LOCAL.getCommandName(), new ChangeLocalCommand());
		commands.put(GO_TO_ACCOUNT.getCommandName(), new GoToAccountCommand());
		commands.put(LOG_OUT.getCommandName(), new LogOutCommand());
		commands.put(GO_TO_PAGE_FOR_LOG_USER.getCommandName(), new GoToPageForLogUserCommand());
		commands.put(REMOVE_USER.getCommandName(), new RemoveUserCommand());
		commands.put(GO_TO_USER_REMOVE_PAGE.getCommandName(), new GoToUserRemovePage());
		commands.put(GO_TO_START_PAGE.getCommandName(), new GoToStartPage());
		commands.put(GO_TO_USER_DATA_PAGE.getCommandName(), new GoToUserDataPageCommand());
		commands.put(GO_TO_SEARCH_PAGE.getCommandName(), new GoToSearchUserPageCommand());
		commands.put(FIND_USER.getCommandName(), new FindUserCommand());
		commands.put(BLOCK_UNLOCK_USER.getCommandName(), new BlockUnlockUserCommand());
		commands.put(GO_TO_ADDING_BOOK_PAGE.getCommandName(), new GoToAddingBookPage());
		commands.put(ADD_BOOK_TO_BASKET.getCommandName(), new AddBookToBasketCommand());
		commands.put(GO_TO_PAGE.getCommandName(), new GoToPageCommand());
		commands.put(REMOVE_BOOK_FROM_BASKET.getCommandName(), new RemoveBookFromBasket());
	}
	
	public Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
