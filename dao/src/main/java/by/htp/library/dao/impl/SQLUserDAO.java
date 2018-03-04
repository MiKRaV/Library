package by.htp.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import by.htp.library.entity.User;
import by.htp.library.dao.UserDAO;
import by.htp.library.dao.db.ConnectionPool;
import by.htp.library.dao.exception.DAOException;
import by.htp.library.dao.helper.DAOUserHelper;
import by.htp.library.entity.UserParameters;

public class SQLUserDAO implements UserDAO{
	
	private ConnectionPool conPool = ConnectionPool.getConnectionPool();

	//��������� ������������
	public User logination(String login, String password) throws DAOException {
		Connection con = null;
		User user = null;
		
		try {
			con = conPool.retrieve();
			
			if(!isUserExist(login)) {
				throw new DAOException("Such a user does not exist");
			}
			
			PreparedStatement pstmt = con.prepareStatement(DAOUserHelper.SELECT_FULL_USER_DATA_BY_LOGIN);
			pstmt.setString(1, login);
        	ResultSet rs = pstmt.executeQuery();
        	rs.next();
        	
        	if(rs.getString(DAOUserHelper.PASSWORD).equals(password)) {
    			String name = rs.getString(DAOUserHelper.NAME);
				String surname = rs.getString(DAOUserHelper.SURNAME);
				String email = rs.getString(DAOUserHelper.EMAIL);
				String userType = rs.getString(DAOUserHelper.TYPE);
				int countBook = Integer.parseInt(rs.getString(DAOUserHelper.COUNT_BOOK));
				String userStatus = rs.getString(DAOUserHelper.USER_STATUS);
				user = new User(login, password, name, surname, email, userType, countBook, userStatus);
    			
    			rs.close();
                pstmt.close();
    		} else {
    			rs.close();
                pstmt.close();
    			throw new DAOException("Inavalid password");
    		}
            
        } catch (SQLException e) {
        	throw new DAOException("Sorry", e);
        } finally {
            conPool.putback(con);
        }
		
		return user;
	}

	//����������� ������������
	public void registration(User user) throws DAOException {
		Connection con = null;
		String email = user.getEmail();
		
		if(isUserExist(user.getLogin())) {
			throw new DAOException("User alreay exist");
		}
		
		try {
			con = conPool.retrieve();
			
			PreparedStatement pstmt = con.prepareStatement(DAOUserHelper.SELECT_EMAIL_BY_EMAIL);
			pstmt.setString(1, email);
        	ResultSet rs = pstmt.executeQuery();
        	
        	if(rs.next() && rs.getString(DAOUserHelper.EMAIL).equals(email)) {
        		throw new DAOException("This e-mail is already registered. Please use another e-mail.");
        	}
        	
        	con.setAutoCommit(false);
        	pstmt = con.prepareStatement(DAOUserHelper.START_TRANSACTION);
        	pstmt.executeUpdate();
        	
        	pstmt = con.prepareStatement(DAOUserHelper.INSERT_USERS);
        	pstmt.setString(1, user.getLogin());
        	pstmt.setString(2, user.getPassword());
        	pstmt.executeUpdate();
        	
        	pstmt = con.prepareStatement(DAOUserHelper.INSERT_USERS_DATA);
        	pstmt.setString(1, user.getName());
        	pstmt.setString(2, user.getSurname());
        	pstmt.setString(3, user.getEmail());
        	pstmt.setString(4, user.getUserType());
        	pstmt.setString(5, Integer.toString(user.getCountBook()));
        	pstmt.setString(6, user.getUserStatus());
        	pstmt.executeUpdate();
        	
        	con.commit();
        	
        	System.out.println("Registration was succesfully!");
        	
        	rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
        	try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	throw new DAOException("Sorry", e);
        } finally {
        	conPool.putback(con);
        }
	}
	
	//��������� ������ �������������
	public ArrayList<User> getAllUsersList() throws DAOException {
		Connection con = null;
		ArrayList<User> userList = null;
		
		try {
			con = conPool.retrieve();
			
			userList = new ArrayList<User>();
			
			PreparedStatement pstmtUser = con.prepareStatement(DAOUserHelper.SELECT_ALL_USERS);
			ResultSet rsUser = pstmtUser.executeQuery();
			
			while(rsUser.next()) {
				String login = rsUser.getString(DAOUserHelper.LOGIN);
				String password = rsUser.getString(DAOUserHelper.PASSWORD);
				String name = rsUser.getString(DAOUserHelper.NAME);
				String surname = rsUser.getString(DAOUserHelper.SURNAME);
				String email = rsUser.getString(DAOUserHelper.EMAIL);
				String userType = rsUser.getString(DAOUserHelper.TYPE);
				userList.add(new User(login, password, name, surname, email, userType));
			}
			
		} catch (SQLException e) {
			throw new DAOException("Sorry! Error when retrieving user list.", e);
		} finally {
			conPool.putback(con);
        }
		
		return userList;
	}
	
	//�������� ������������
	public void removeUser(String login) throws DAOException {
		Connection con = null;
		
		if(!isUserExist(login)) {
			throw new DAOException("Such a user does not exist");
		}
		
		try {
			con = conPool.retrieve();
			
			PreparedStatement pstmt = con.prepareStatement(DAOUserHelper.SELECT_ID_USER_BY_LOGIN);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString(DAOUserHelper.ID_USER);
			
			con.setAutoCommit(false);
        	pstmt = con.prepareStatement(DAOUserHelper.START_TRANSACTION);
        	pstmt.executeUpdate();
        	
        	pstmt = con.prepareStatement(DAOUserHelper.DELETE_USER_DATA_BY_ID);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
        	
        	pstmt = con.prepareStatement(DAOUserHelper.DELETE_USER_BY_ID);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			con.commit();
			
			rs.close();
            pstmt.close();
        	
        	System.out.println("User deletion successful!");
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DAOException("Sorry", e);
		} finally {
			conPool.putback(con);
        }
	}

	//��������, ���������� �� ������������
	public boolean isUserExist(String login) throws DAOException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = conPool.retrieve();
			
			pstmt = con.prepareStatement(DAOUserHelper.SELECT_LOGIN_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getString(DAOUserHelper.LOGIN).equals(login)) {
				pstmt.close();
	        	rs.close();
				return true;
			}
			
			pstmt.close();
        	rs.close();
        		
		} catch (SQLException e) {
			throw new DAOException("Sorry", e);
		} finally {
			conPool.putback(con);
			
        }
		
		return false;
	}

	//�������������� ������ ������������
	@SuppressWarnings("resource")
	public void changeUserData(User user, UserParameters data, String dataValue) throws DAOException {
		
		Connection con = null;
		
		try {
			con = conPool.retrieve();
			PreparedStatement pstmt = con.prepareStatement(DAOUserHelper.SELECT_ID_USER_BY_LOGIN);
			pstmt.setString(1, user.getLogin());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString(DAOUserHelper.ID_USER);
			
			switch(data) {
			case PASSWORD:
				pstmt = con.prepareStatement(DAOUserHelper.UPDATE_PASSWORD_BY_ID_USER);
	        	break;
			case NAME:
				pstmt = con.prepareStatement(DAOUserHelper.UPDATE_NAME_BY_ID_USER);
	        	break;
			case SURNAME:
				pstmt = con.prepareStatement(DAOUserHelper.UPDATE_SURNAME_BY_ID_USER);
	        	break;
			case EMAIL:
				pstmt = con.prepareStatement(DAOUserHelper.UPDATE_EMAIL_BY_ID_USER);
	        	break;
			default:
				break;
			}
			
			pstmt.setString(1, dataValue);
        	pstmt.setString(2, id);
        	pstmt.executeUpdate();
			
			pstmt.close();
			rs.close();
			
		} catch (SQLException e) {
			throw new DAOException("Sorry! Couldn't make changes to the database", e);
		} finally {
			conPool.putback(con);
        }
		
	}

	//����� ������������ �� ������
	public User findUserByLogin(String login) throws DAOException {
		Connection con = null;
		User user = null;
		
		if(!isUserExist(login)) {
			throw new DAOException("Such a user does not exist");
		}
		
		try {
			con = conPool.retrieve();
			
			PreparedStatement pstmt = con.prepareStatement(DAOUserHelper.SELECT_FULL_USER_DATA_BY_LOGIN);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			String name = rs.getString(DAOUserHelper.NAME);
			String surname = rs.getString(DAOUserHelper.SURNAME);
			String email = rs.getString(DAOUserHelper.EMAIL);
			String userType = rs.getString(DAOUserHelper.TYPE);
			int countBook = Integer.parseInt(rs.getString(DAOUserHelper.COUNT_BOOK));
			String userStatus = rs.getString(DAOUserHelper.USER_STATUS);
			user = new User(login, name, surname, email, userType, countBook, userStatus);
			
			rs.close();
            pstmt.close();
			
		} catch (SQLException e) {
			throw new DAOException("Sorry", e);
        } finally {
            conPool.putback(con);
        }
		
		return user;
	}

	//����������/������������� ������������
	public void blockUnlockUser(String login) throws DAOException {
		Connection con = null;
		
		if(!isUserExist(login)) {
			throw new DAOException("Such a user does not exist");
		}
		
		try {
			con = conPool.retrieve();
			
			PreparedStatement pstmt = con.prepareStatement(DAOUserHelper.SELECT_FULL_USER_DATA_BY_LOGIN);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			String id = rs.getString(DAOUserHelper.ID_USER);
			String userStatus = rs.getString(DAOUserHelper.USER_STATUS);
			
			pstmt = con.prepareStatement(DAOUserHelper.UPDATE_USER_STATUS_BY_ID_USER);
			pstmt.setString(2, id);
			
			if(userStatus.equals(DAOUserHelper.USER_STATUS_ACTIVE)) {
				pstmt.setString(1, DAOUserHelper.USER_STATUS_BLOCKED);
			} else if(userStatus.equals(DAOUserHelper.USER_STATUS_BLOCKED)) {
				pstmt.setString(1, DAOUserHelper.USER_STATUS_ACTIVE);
			}
        	
        	pstmt.executeUpdate();
        	
        	rs.close();
            pstmt.close();
		} catch (SQLException e) {
			throw new DAOException("Sorry", e);
		} finally {
            conPool.putback(con);
        }
		
	}

}
