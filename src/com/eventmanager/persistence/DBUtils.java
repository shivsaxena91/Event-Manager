package com.eventmanager.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.eventmanager.utilities.Constants;

public class DBUtils {
	
	private static Connection connection = null;
	
	private DBUtils() {}
	
	/**
	 * Open a database connection.
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 */
	public static Connection openDBConnection() {
		if(connection == null) {
			try {
				Class.forName(Constants.DB_DRIVER);
				connection = (Connection) DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return connection;
	}

	/**
	 * Close the database connection.
	 * @param connection
	 * @throws SQLException
	 */
	public static void closeDBConnection(Connection connection){
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}