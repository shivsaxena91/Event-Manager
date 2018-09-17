package com.eventmanager.notification;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.eventmanager.utilities.Constants;

/**
 * @author Ajinkya
 *
 */
public class PasswordResetEmail implements Email{
	private String emailAddress = null;
	private String password = null;
	
	public PasswordResetEmail(String emailAddress, String password) {
		this.emailAddress = emailAddress;
		this.password = password;
	}

	private String getEmailBody(){
		return "Hello, \n\nPlease, use the following credentials to login into EventManager application."
				+ "\n\nEmail Address: "+this.emailAddress+"\n"
				+ "Password: "+this.password+"\n\n"
				+ "You can set the password of your choice by using Rest Password functionality after successful login.\n\n"
				+ "Thanks & regards,\nEventManager Admin";
	}
	
	@Override
	public boolean sendEmail(Object object) {
		if(object instanceof Session) {
			Session session = (Session)object;
			try {
				Message email = new MimeMessage(session);
				email.setFrom(new InternetAddress(Constants.EVENT_MANAGER_EMAIL));
				email.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(this.emailAddress));
				email.setSubject(Constants.FORGOT_PWD_EMAIL_SUBJECT);
				email.setText(this.getEmailBody());
				
				Transport.send(email);
			} catch (MessagingException e) {
				return false;
			}
		}
		return false;
	}
}
