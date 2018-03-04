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
import by.htp.library.controller.command.impl.IsUserExistCommand;
import by.htp.library.controller.command.impl.LogOutCommand;
import by.htp.library.controller.command.impl.LoginationCommand;
import by.htp.library.controller.command.impl.RegistrationCommand;
import by.htp.library.controller.command.impl.RemoveUserCommand;
import by.htp.library.controller.command.impl.SearchByTitleCommand;
import by.htp.library.controller.command.impl.StartAppCommand;

public class CommandProvider {
	
	private Map<String, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put("logination", new LoginationCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("addBook", new AddBookCommand());
		commands.put("searchByTitle", new SearchByTitleCommand());
		commands.put("isUserExist", new IsUserExistCommand());
		commands.put("changeUserData", new ChangeUserDataCommand());
		commands.put("startAppLogination", new StartAppCommand());
		commands.put("startAppRegistration", new StartAppCommand());
		commands.put("getAllUsers", new GetAllUsersCommand());
		commands.put("getAllBooks", new GetAllBooksCommand());
		commands.put("changeLocal", new ChangeLocalCommand());
		commands.put("goToAccount", new GoToAccountCommand());
		commands.put("logOutCommand", new LogOutCommand());
		commands.put("goToPageForLogUser", new GoToPageForLogUserCommand());
		commands.put("removeUser", new RemoveUserCommand());
		commands.put("goToUserRemovePage", new GoToUserRemovePage());
		commands.put("goToStartPage", new GoToStartPage());
		commands.put("goToUserDataPage", new GoToUserDataPageCommand());
		commands.put("goToSearchUserPage", new GoToSearchUserPageCommand());
		commands.put("findUser", new FindUserCommand());
		commands.put("blockUnlockUser", new BlockUnlockUserCommand());
		commands.put("goToAddingBookPage", new GoToAddingBookPage());
	}
	
	public Command getCommand(String commandName) {
		
		return commands.get(commandName);
	}

}
