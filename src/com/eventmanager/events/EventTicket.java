package com.eventmanager.events;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import com.eventmanager.notification.EmailNotification;
import com.eventmanager.notification.EventTicketEmail;
import com.eventmanager.persistence.EventDBOperations;
import com.eventmanager.persistence.PurchaseDBOperations;
import com.eventmanager.users.User;

public class EventTicket {
	private String ticketId = null;
	private User user = null;
	private Event event = null;
	private int numberOfTickets = 0;
	private double totalCost = 0.0;
	private Timestamp purchaseTime = null;
	
	public EventTicket(User user, Event event, int numberOfTickets, double totalCost,
			Timestamp purchaseTime) {
		this.ticketId = UUID.randomUUID().toString();
		this.user = user;
		this.event = event;
		this.numberOfTickets = numberOfTickets;
		this.totalCost = totalCost;
		this.purchaseTime = purchaseTime;
	}

	public EventTicket(String ticketId, User user, Event event, int numberOfTickets, double totalCost,
			Timestamp purchaseTime) {
		this.ticketId = ticketId;
		this.user = user;
		this.event = event;
		this.numberOfTickets = numberOfTickets;
		this.totalCost = totalCost;
		this.purchaseTime = purchaseTime;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Timestamp getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurcahseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	
	public boolean purchaseTickets(Connection connection) {
		boolean purchaseStatus = false;
		try {
			purchaseStatus = PurchaseDBOperations.purchaseTickets(this, connection);
			if(purchaseStatus) {
				EventDBOperations.reduceEventTickets(this.getNumberOfTickets(), this.getEvent().getEventId(), connection);
				EventTicketEmail eventTicketEmail = new EventTicketEmail(this);
				EmailNotification emailNotification = new EmailNotification();
				emailNotification.sendEmail(eventTicketEmail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return purchaseStatus;
	}
	
	public static ArrayList<EventTicket> getTickets(User user, Connection connection){
		ArrayList<EventTicket> ticketList = null;
		try {
			Object object = PurchaseDBOperations.getTickets(user, connection);
			if(object instanceof ArrayList<?>) {
				ticketList = (ArrayList<EventTicket>)object;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticketList;
	}
}
