package com.eventmanager.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.RandomStringUtils;

import com.eventmanager.users.EventOrganizer;
import com.eventmanager.users.EventParticipant;
import com.eventmanager.users.User;
import com.eventmanager.users.UserAbstractFactory;
import com.eventmanager.users.UserType;
import com.eventmanager.utilities.Constants;

public class UserDBOperations {

	public static boolean createNewUser(UserAbstractFactory user, Connection connection) throws SQLException {
		boolean isCreated = true;
		
		PreparedStatement preparedStatement = null;
		 try {
		      String query = "insert into USER(user_id, first_name, last_name, user_type, email_address, password) values(?,?,?,?,?,?)";
		      preparedStatement = connection.prepareStatement(query);
		      
		      preparedStatement.setString(1, user.getUserId());
		      preparedStatement.setString(2, user.getFirstName());
		      preparedStatement.setString(3, user.getLastName());
		      preparedStatement.setString(4, user.getUserType().toString());
		      preparedStatement.setString(5, user.getEmailAddress());
		      preparedStatement.setString(6, user.getPassword());
		      
		      preparedStatement.executeUpdate();
		 } 
		 catch (SQLException sqle) {
			 isCreated = false;
		 }
		 finally {
			 if(preparedStatement!=null){
				 preparedStatement.close(); 
			 }
		}
		return isCreated;
	}
	
	public static Object getUserByCredentials(String emailAddress, String password, Connection connection) throws SQLException {
		String query = "select * from USER where email_address = '"+emailAddress+"' and password = '"+password+"'";
		return getUser(emailAddress, query, connection);
	}
	
	public static Object getUserByEmailAddress(String emailAddress, Connection connection) throws SQLException {
		String query = "select * from USER where email_address = '"+emailAddress+"'";
		return getUser(emailAddress, query, connection);
	}
	
	private static Object getUser(String emailAddress, String query, Connection connection) throws SQLException {
		User user = null;
		Statement statement = null;
	    ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	        	String userId = resultSet.getString("user_id");
	        	String firstName = resultSet.getString("first_name");
	        	String lastName = resultSet.getString("last_name");
	        	String userType = resultSet.getString("user_type");
	        	
	        	switch (userType) {
				case Constants.EVENT_ORGANIZER:
					user = new EventOrganizer(userId, firstName, lastName, UserType.EVENT_ORGANIZER, emailAddress);
					break;
				case Constants.EVENT_PARTICIPANT:
					user = new EventParticipant(userId, firstName, lastName, UserType.EVENT_PARTICIPANT, emailAddress);
					break;
				default:
					break;
				}
	        }
		} catch (SQLException e) {
			return Constants.DB_ERROR;
		}
		finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
		}
		return user;
	}
	
	public static String resetPassword(String emailAddress, String newPassword, Connection connection) throws SQLException {
		if(newPassword == null) {
			newPassword = RandomStringUtils.random(8, true, true);
		}
		User user = (User) getUserByEmailAddress(emailAddress, connection);
		if(user != null) {
			PreparedStatement preparedStatement = null;
			 try {
			      String query = "update User set password = '"+newPassword+"' where email_address = '"+emailAddress+"'";
			      preparedStatement = connection.prepareStatement(query);
			      preparedStatement.executeUpdate();
			 } 
			 catch (SQLException sqle) {
				 return null;
			 }
			 finally {
				 if(preparedStatement!=null){
					 preparedStatement.close(); 
				 }
			}
		}
		else {
			return null;
		}
		return newPassword;	
	}
	
	private static String getCurrentPassword(String emailAddress, Connection connection) throws SQLException {
		Statement statement = null;
	    ResultSet resultSet = null;
	    String password = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select password from USER where email_address = '"+emailAddress+"'");
	        while (resultSet.next()) {
	        	password = resultSet.getString("password");
	        }
		} catch (SQLException e) {
			return null;
		}
		finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
		}
		return password;
	}
	
	public static boolean replaceCurentPasswordByNewPassword(String emailAddress, String currentPassword, String newPassword, Connection connection) throws SQLException {
		boolean resetFlag = true;
		String currentPwd = getCurrentPassword(emailAddress, connection);
		if(currentPwd != null && currentPwd.equals(currentPassword)) {
			resetPassword(emailAddress, newPassword, connection);
		}
		else {
			resetFlag = false;
		}
		return resetFlag;	
	}
}
