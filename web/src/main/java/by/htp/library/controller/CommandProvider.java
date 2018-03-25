package by.htp.library.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.library.controller.command.Command;
import by.htp.library.controller.command.impl.AddBookCommand;
import by.htp.library.controller.command.impl.BlockUnlockUserCommand;
import by.htp.library.controller.command.impl.ChangeLocalCommand;
import by.htp.library.controller.command.impl.ChangeUserDataCommand;
import by.htp.library.controller.command.impl.FindUserCommand;
import by.htp.library.controller.command.impl.GetAllBooksCommand;
import by.htp.library.controller.command.impl.GetAllUsersCommand;
import by.htp.library.controller.command.impl.GoToAccountCommand;
import by.htp.library.controller.command.impl.GoToAddingBookPage;
import by.htp.library.controller.command.impl.GoToPageForLogUserCommand;
import by.htp.library.controller.command.impl.GoToSearchUserPageCommand;
import by.htp.library.controller.command.impl.GoToStartPage;
import by.htp.library.controller.command.impl.GoToUserDataPageCommand;
import by.htp.library.controller.command.impl.GoToUserRemovePage;
import by.htp.library.controller.command.impl.LogOutCommand;
import by.htp.library.controller.command.impl.LoginationCommand;
import by.htp.library.controller.command.impl.RegistrationCommand;
import by.htp.library.controller.command.impl.RemoveUserCommand;
import by.htp.library.controller.command.impl.SearchByTitleCommand;
import by.htp.library.controller.command.impl.StartAppCommand;
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
	}
	
	public Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
