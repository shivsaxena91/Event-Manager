package com.eventmanager.users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.eventmanager.notification.EmailNotification;
import com.eventmanager.notification.PasswordResetEmail;
import com.eventmanager.persistence.UserDBOperations;
import com.eventmanager.utilities.Constants;

public class EventParticipant extends User {
	private String userId = null;
	private String firstName = null;
	private String lastName = null;
	private Enum<UserType> userType = null;
	private String emailAddress = null;
	private String password = null;
	
	public EventParticipant() {
	}
	
	public EventParticipant(String firstName, String lastName, Enum<UserType> userType, String emailAddress, String password) {
		this.userId = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	
	public EventParticipant(String userId, String firstName, String lastName, Enum<UserType> userType, String emailAddress) {
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
	public Object[] getUserByCredentials(String emailAddress, String password, Connection connection) {
		Object resultStatus[] = null;
		
		try {
			Object resultObject = UserDBOperations.getUserByCredentials(emailAddress, password, connection);
			if(resultObject != null) {
				if (resultObject instanceof String) {
					resultStatus = new Object[2];
					resultStatus[0] = (String) resultObject;
					resultStatus[1] = Constants.LOGIN_JSP;
				}
				else if(resultObject instanceof User) {
					resultStatus = new Object[3];
					resultStatus[0] = null;
					if(resultObject instanceof EventOrganizer) {
						resultStatus[1] = Constants.ORGANIZER_HOMEPAGE_JSP;
					}
					
					if(resultObject instanceof EventParticipant) {
						resultStatus[1] = Constants.PARTICIPANT_HOMEPAGE_JSP;
					}
					
					resultStatus[2] = (User)resultObject;
				}
				return resultStatus;
			}
			else {
				resultStatus = new Object[2];
				resultStatus[0] = Constants.INVALID_ID_PASSWORD;
				resultStatus[1] = Constants.LOGIN_JSP;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultStatus;
	}
	
	@Override
	public boolean forgotPassword(String emailAddress, Connection connection) {
		try {
			String newPassword = UserDBOperations.resetPassword(emailAddress, null, connection);
			if(newPassword != null) {
				PasswordResetEmail passwordResetEmail = new PasswordResetEmail(emailAddress, newPassword);
				EmailNotification emailNotification = new EmailNotification();
				emailNotification.sendEmail(passwordResetEmail);
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean resetPassword(String currentPassword, String newPassword, Connection connection) {
		try {
			return UserDBOperations.replaceCurentPasswordByNewPassword(this.getEmailAddress(), currentPassword, newPassword, connection);
		} catch (SQLException e) {
			return false;
		}
	}
}