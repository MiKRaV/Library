package by.htp.library.dao.helper;

public class UserDAOHelper {
	

	public static final String SELECT_STATUS_BY_LOGIN = "SELECT u.status FROM User u WHERE u.login=:login";

	public static final String UPDATE_USER_STATUS_BY_LOGIN = "UPDATE User u SET u.status=:status WHERE u.login=:login";

	public static final String MESSAGE_INVALID_PASSWORD = "Invalid password";
	public static final String MESSAGE_USER_DOES_NOT_EXIST = "Such a user does not exist";
	public static final String MESSAGE_USER_EXIST = "User already exist";
	public static final String MESSAGE_USER_DELETED = "Operation impossible: user deleted";
	public static final String MESSAGE_EMAIL_REGISTERED = "This e-mail is already registered. Please use another e-mail.";
	public static final String MESSAGE_FAIL_USER_SEARCHING = "Failure when searching for a user";

}
