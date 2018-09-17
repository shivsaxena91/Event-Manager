package com.eventmanager.users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.eventmanager.persistence.UserDBOperations;
import com.eventmanager.utilities.Constants;

public class ParticipantFactory implements UserAbstractFactory {
	private String userId = null;
	private String firstName = null;
	private String lastName = null;
	private Enum<UserType> userType = null;
	private String emailAddress = null;
	private String password = null;
	
	public ParticipantFactory(String firstName, String lastName, Enum<UserType> userType, String emailAddress, String password) {
		this.userId = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	
	public ParticipantFactory(String userId, String firstName, String lastName, Enum<UserType> userType, String emailAddress) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.emailAddress = emailAddress;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Enum<UserType> getUserType() {
		return userType;
	}

	public void setUserType(Enum<UserType> userType) {
		this.userType = userType;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String[] createNewUser(Connection connection) {
		String resultStatus[] = new String[2];
		
		try {
			Object resultObject = UserDBOperations.getUserByEmailAddress(this.getEmailAddress(), connection);
			if(resultObject != null) {
				if (resultObject instanceof String) {
					resultStatus[0] = (String) resultObject;
					resultStatus[1] = Constants.LOGIN_JSP;
				}
				else if(resultObject instanceof User) {
					resultStatus[0] = Constants.USER_ALREADY_EXISTS;
					resultStatus[1] = Constants.REGISTRATION_JSP;
				}
				return resultStatus;
			}
			
			if(UserDBOperations.createNewUser(this, connection)) {
				resultStatus[0] = Constants.SIGN_UP_SUCCESS;
			}
			else {
				resultStatus[0] = Constants.DB_ERROR;
			}
			resultStatus[1] = Constants.LOGIN_JSP;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultStatus;
	}

}
