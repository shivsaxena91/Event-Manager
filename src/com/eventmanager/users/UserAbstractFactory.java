package com.eventmanager.users;

import java.sql.Connection;

public interface UserAbstractFactory {
	public String[] createNewUser(Connection connection);
	
	public String getUserId();

	public String getEmailAddress();

	public String getFirstName();

	public String getLastName();

	public Enum<UserType> getUserType();

	public String getPassword();
}
