package by.htp.library.dao.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import by.htp.library.dao.exception.DAOException;

public class ConnectionPool {
	
	private static ConnectionPool conPool;
	private Vector<Connection> availableConns = new Vector<Connection>();
	private Vector<Connection> usedConns = new Vector<Connection>();

	private ConnectionPool(int initConnCnt) {
		try {
			driverConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < initConnCnt; i++) {
			availableConns.addElement(getConnection());
		}
	}
	
	public static ConnectionPool getConnectionPool() {
		if(conPool == null) {
			conPool = new ConnectionPool(2);
		}
		
		return conPool;
	}
	
	private static void driverConnection() throws DAOException {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			throw new DAOException("Error when connecting diver" , e);
		}
	}

	private Connection getConnection() {
		Connection con = null;

		Properties properties = new Properties();
		Thread currentTread = Thread.currentThread();
		ClassLoader classLoader = currentTread.getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("/resources/properties/DB.properties");

		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = properties.getProperty("url");
		String userDB = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		try {
			con = DriverManager.getConnection(url, userDB, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public synchronized Connection retrieve() {
		Connection newConn = null;
		if (availableConns.size() == 0) {
			newConn = getConnection();
		} else {
			newConn = (Connection) availableConns.lastElement();
			availableConns.removeElement(newConn);
		}
		usedConns.addElement(newConn);
		return newConn;
	}

	public synchronized void putback(Connection c) throws NullPointerException {
	if (c != null) {
		if (usedConns.removeElement(c)) {
			availableConns.addElement(c);
		} else {
			throw new NullPointerException("Connection not in the usedConns array");
		}
	 } 
    }

	public int getAvailableConnsCnt() {
		return availableConns.size();
	}
}
