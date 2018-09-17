package com.eventmanager.notification;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.eventmanager.utilities.Constants;

public class EmailNotification implements Email{
	public EmailNotification() {
	}
	
	public Session getSession() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constants.USERNAME, Constants.PWD);
			}
		  });
		return session;
	}

	@Override
	public boolean sendEmail(Object object) {
		if(object instanceof Email) {
			Email email = (Email)object;
			Session session = getSession();
			return email.sendEmail(session);
		}
		return false;
	}
}
