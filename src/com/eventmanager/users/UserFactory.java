package com.eventmanager.users;

import java.sql.Connection;

public class UserFactory {
	public static String[] getUser(UserAbstractFactory userAbstractFactory, Connection connection) {
		return userAbstractFactory.createNewUser(connection);
	}
}