package com.eventmanager.users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.eventmanager.notification.EmailNotification;
import com.eventmanager.notification.PasswordResetEmail;
import com.eventmanager.persistence.UserDBOperations;
import com.eventmanager.utilities.Constants;

public abstract class User {
	
	public abstract Object[] getUserByCredentials(String emailAddress, String password, Connection connection);
	
	public abstract boolean forgotPassword(String emailAddress, Connection connection);
	
	public abstract boolean resetPassword(String currentPassword, String newPassword, Connection connection);
	
	public abstract String getUserId();

	public abstract String getEmailAddress();

	public abstract String getFirstName();

	public abstract String getLastName();

	public abstract Enum<UserType> getUserType();

	public abstract String getPassword();
}