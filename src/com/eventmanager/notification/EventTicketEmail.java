package com.eventmanager.notification;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.eventmanager.events.Event;
import com.eventmanager.events.EventTicket;
import com.eventmanager.utilities.Constants;

public class EventTicketEmail implements Email{
	private EventTicket eventTicket = null;
	private String emailAddress = null;
	
	public EventTicketEmail(EventTicket eventTicket) {
		this.emailAddress = eventTicket.getUser().getEmailAddress();
		this.eventTicket = eventTicket;
	}

	private String getEmailBody() {
		Event event = this.eventTicket.getEvent();
		return "Dear "+eventTicket.getUser().getFirstName()+""
				+ ",\n\nPlease, find the tickets for the event - "+event.getEventName()+" below:\n\n"
				+ "**************************************\n"
				+ "**************************************\n"
				+ "Event Name: "+event.getEventName()+"\n"
				+ "Number of Tickets: "+eventTicket.getNumberOfTickets()+"\n"
				+ "Amount Paid: "+eventTicket.getTotalCost()+"\n"
				+ "Purchase Time: "+eventTicket.getPurchaseTime()+"\n"
				+ "Event Location: "+event.getEventLocation()+"\n"
				+ "Event Date: "+event.getEventDate()+"\n"
				+ "Event Time: "+event.getEventTime()+"\n"
				+ "Event Description: "+event.getEventDescription()+"\n"
				+ "**************************************\n"
				+ "**************************************\n\n"
				+ "Thanks & regards,\n"
				+ "Event Manager Team";
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
				email.setSubject(eventTicket.getEvent().getEventName()+Constants.EVENT_TICKETS);
				email.setText(getEmailBody());
				
				Transport.send(email);
			} catch (MessagingException e) {
				return false;
			}
		}
		return false;
	}
}