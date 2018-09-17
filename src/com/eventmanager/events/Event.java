package com.eventmanager.events;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.eventmanager.persistence.EventDBOperations;
import com.eventmanager.users.User;
import com.eventmanager.utilities.Constants;

public class Event{
	
	private String eventId = null;
	private String eventName = null;
	private String eventDescription = null;
	private String eventLocation = null;
	private Date eventDate = null;
	private String eventTime = null;
	private double ticketPrice = 0.0;
	private int eventCapacity = 0;
	private int availableTickets = 0;
	private User organizer = null;
	
	public Event() {
	}
	
	public Event(String eventName, String eventDescription, String eventLocation, Date eventDate, String eventTime,
			double ticketPrice, int eventCapacity) {
		this.eventId = UUID.randomUUID().toString();
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventLocation = eventLocation;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.ticketPrice = ticketPrice;
		this.eventCapacity = eventCapacity;
		this.availableTickets = eventCapacity;
	}
	
	public Event(String eventId, String eventName, String eventDescription, String eventLocation, Date eventDate,
			String eventTime, double ticketPrice, int eventCapacity, int availableTickets) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventLocation = eventLocation;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.ticketPrice = ticketPrice;
		this.eventCapacity = eventCapacity;
		this.availableTickets = availableTickets;
	}
	
	public Event(String eventName, String eventDescription, String eventLocation, Date eventDate,
			String eventTime, double ticketPrice, int eventCapacity, User organizer) {
		this.eventId = UUID.randomUUID().toString();
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventLocation = eventLocation;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.ticketPrice = ticketPrice;
		this.eventCapacity = eventCapacity;
		this.availableTickets = eventCapacity;
		this.organizer = organizer;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public int getEventCapacity() {
		return eventCapacity;
	}

	public void setEventCapacity(int eventCapacity) {
		this.eventCapacity = eventCapacity;
	}

	public int getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}
	
	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	public String[] createNewEvent(User user, Connection connection) {
		String resultStatus[] = new String[2];
		
		try {
			Object resultObject = EventDBOperations.getEventByNameAndDate(this.getEventName(), this.getEventDate(), connection);
			if(resultObject != null) {
				if (resultObject instanceof String) {
					resultStatus[0] = (String) resultObject;
					resultStatus[1] = Constants.CREATE_NEW_EVENT_JSP;
				}
				else if(resultObject instanceof Event) {
					resultStatus[0] = Constants.EVENT_ALREADY_EXISTS;
					resultStatus[1] = Constants.CREATE_NEW_EVENT_JSP;
				}
				return resultStatus;
			}
			
			if(EventDBOperations.createNewEvent(user, this, connection)) {
				resultStatus[1] = Constants.ORGANIZER_HOMEPAGE_JSP;
				resultStatus[0] = Constants.EVENT_CREATED_SUCCESSFULLY;
			}
			else {
				resultStatus[1] = Constants.CREATE_NEW_EVENT_JSP;
				resultStatus[0] = Constants.DB_ERROR;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultStatus;
	}
	
	public static ArrayList<Event> getUpcomingEvents(Connection connection){
		ArrayList<Event> eventList = null;
		try {
			Object resultObject = EventDBOperations.getAllUpcomingEvents(connection);
			if(resultObject != null && resultObject instanceof ArrayList<?>) {
				eventList = (ArrayList<Event>)resultObject;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventList;
	}
	
	public static ArrayList<Event> getEventListByOrganizerId(String userId, Connection connection){
		ArrayList<Event> eventList = null;
		try {
			Object resultObject = EventDBOperations.getEventListByOrganizerId(userId, connection);
			if(resultObject != null && resultObject instanceof ArrayList<?>) {
				eventList = (ArrayList<Event>)resultObject;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventList;
	}
	
	public static ArrayList<Event> searchEvents(String keyword, Connection connection){
		ArrayList<Event> eventList = null;
		try {
			Object resultObject = EventDBOperations.searchEvent(keyword, connection);
			if(resultObject != null && resultObject instanceof ArrayList<?>) {
				eventList = (ArrayList<Event>)resultObject;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eventList;
	}
}
