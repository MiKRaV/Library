package by.htp.library.dao.helper;

public class DAOUserHelper {
	
	public static final String ID_USER = "id_user";
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String EMAIL = "email";
	public static final String TYPE = "type";
	public static final String COUNT_BOOK = "count_book";
	public static final String USER_STATUS = "user_status";
	public static final String USER_STATUS_ACTIVE = "active";
	public static final String USER_STATUS_BLOCKED = "blocked";
	
	public static final String SET_AUTOCOMMIT_0 = "SET AUTOCOMMIT=0";
	public static final String SET_AUTOCOMMIT_1 = "SET AUTOCOMMIT=1";
	
	public static final String START_TRANSACTION = "START TRANSACTION";
	public static final String COMMIT = "COMMIT";
	public static final String ROLLBACK = "ROLLBACK";
	
	public static final String SELECT_LOGIN_BY_LOGIN = "SELECT login FROM users WHERE login=?";
	public static final String SELECT_ID_USER_BY_LOGIN = "SELECT id_user FROM users WHERE login=?";
	
	public static final String SELECT_ALL_USERS = "SELECT * FROM users" + 
			" JOIN users_data" + 
			" USING (id_user)";
	
	public static final String SELECT_FULL_USER_DATA_BY_LOGIN = "SELECT * FROM" + 
			" (SELECT * FROM users WHERE login=?) u" + 
			" JOIN users_data USING (id_user)";

	public static final String SELECT_EMAIL_BY_EMAIL = "SELECT email FROM users_data WHERE email = ?";
	
	public static final String INSERT_USERS = "INSERT INTO users (login, password) VALUES (?,?)";
	public static final String INSERT_USERS_DATA = "INSERT INTO users_data (name, surname, email, type, count_book, user_status)" + 
			" VALUES (?,?,?,?,?,?)";
	
	public static final String UPDATE_PASSWORD_BY_ID_USER = "UPDATE users SET password=? WHERE id_user=?";
	public static final String UPDATE_NAME_BY_ID_USER = "UPDATE users_data SET name=? WHERE id_user=?";
	public static final String UPDATE_SURNAME_BY_ID_USER = "UPDATE users_data SET surname=? WHERE id_user=?";
	public static final String UPDATE_EMAIL_BY_ID_USER = "UPDATE users_data SET email=? WHERE id_user=?";
	public static final String UPDATE_USER_STATUS_BY_ID_USER = "UPDATE users_data SET user_status=? WHERE id_user=?";
	
	public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id_user=?";
	public static final String DELETE_USER_DATA_BY_ID = "DELETE FROM users_data WHERE id_user=?";
}
