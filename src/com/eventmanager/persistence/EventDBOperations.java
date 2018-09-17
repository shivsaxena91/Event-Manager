package com.eventmanager.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.eventmanager.events.Event;
import com.eventmanager.users.User;
import com.eventmanager.utilities.Constants;

public class EventDBOperations {
	public static boolean createNewEvent(User user, Event event, Connection connection) throws SQLException {
		boolean isCreated = true;
		
		PreparedStatement preparedStatement = null;
		 try {
		      String query = "insert into EVENT(event_id, name, description, location, event_date, event_time, ticket_price, capacity, available_tickets, user_id) values(?,?,?,?,?,?,?,?,?,?)";
		      preparedStatement = connection.prepareStatement(query);
		      
		      preparedStatement.setString(1, event.getEventId());
		      preparedStatement.setString(2, event.getEventName());
		      preparedStatement.setString(3, event.getEventDescription());
		      preparedStatement.setString(4, event.getEventLocation());
		      preparedStatement.setDate(5, event.getEventDate());
		      preparedStatement.setString(6, event.getEventTime());
		      preparedStatement.setDouble(7, event.getTicketPrice());
		      preparedStatement.setInt(8, event.getEventCapacity());
		      preparedStatement.setInt(9, event.getAvailableTickets());
		      preparedStatement.setString(10, user.getUserId());
		      
		      preparedStatement.executeUpdate();
		 } 
		 catch (SQLException sqle) {
			 isCreated = false;
		 }
		 finally {
			 if(preparedStatement!=null){
				 preparedStatement.close(); 
			 }
		}
		return isCreated;
	}
	
	public static Object getEventList(String query, Connection connection) throws SQLException {
		List<Event> eventList = new ArrayList<>();
		Statement statement = null;
	    ResultSet resultSet = null;
	   
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	        	String eventId = resultSet.getString("event_id");
	        	String eventName = resultSet.getString("name");
	        	String eventDescription = resultSet.getString("description");
	        	String eventLocation = resultSet.getString("location");
	        	Date eventDate = resultSet.getDate("event_date");
	        	String eventTime = resultSet.getString("event_time");
	        	double ticketPrice = resultSet.getDouble("ticket_price");
	        	int eventCapacity = resultSet.getInt("capacity");
	        	int availableTickets = resultSet.getInt("available_tickets");
	        	
	        	eventList.add(new Event(eventId, eventName, eventDescription, eventLocation, eventDate, eventTime, ticketPrice, eventCapacity, availableTickets));
	        }
		} catch (SQLException e) {
			return Constants.DB_ERROR;
		}
		finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
		}
		return eventList;
	}
	
	private static Event getEvent(String query, Connection connection) throws SQLException {
		Event event = null;
		Statement statement = null;
	    ResultSet resultSet = null;
	    
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	        	String eventId = resultSet.getString("event_id");
	        	String eventName = resultSet.getString("name");
	        	String eventDescription = resultSet.getString("description");
	        	String eventLocation = resultSet.getString("location");
	        	String eventTime = resultSet.getString("event_time");
	        	Date eventDate = resultSet.getDate("event_date");
	        	double ticketPrice = resultSet.getDouble("ticket_price");
	        	int eventCapacity = resultSet.getInt("capacity");
	        	int availableTickets = resultSet.getInt("available_tickets");
	        	
	        	event = new Event(eventId, eventName, eventDescription, eventLocation, eventDate, eventTime, ticketPrice, eventCapacity, availableTickets);
	        }
		} catch (SQLException e) {
			return null;
		}
		finally{
			if(resultSet!=null){
				resultSet.close();
			}
			if(statement!=null){
				statement.close();
			}
		}
		return event;
	}
	
	public static boolean reduceEventTickets(int numberOfTickets, String eventId, Connection connection) throws SQLException {
		boolean isSuccessful = true;
		PreparedStatement preparedStatement = null;
		 try {
		      String query = "update Event set available_tickets = available_tickets - "+numberOfTickets+" where event_id = '"+eventId+"'";
		      preparedStatement = connection.prepareStatement(query);
		      preparedStatement.executeUpdate();
		 } 
		 catch (SQLException sqle) {
			 isSuccessful = false;
		 }
		 finally {
			 if(preparedStatement!=null){
				 preparedStatement.close(); 
			 }
		}
		return isSuccessful;
	}
	
	public static Object getEventByNameAndDate(String eventName, Date eventDate, Connection connection) throws SQLException {
		String query = "select * from EVENT where name = '"+eventName+"' AND event_date = '"+eventDate+"'";
		return getEvent(query, connection);				
	}
	
	public static Object searchEvent(String keyword, Connection connection) throws SQLException {
		 String query = "select * from EVENT where name like '%"+keyword+"%' or description like '%"+keyword+"%' or location like '%"+keyword+"%'";
		 return getEventList(query, connection);
	}
	
	public static Object getEventListByOrganizerId(String userId, Connection connection) throws SQLException {
		 String query = "select * from EVENT where user_id = '"+userId+"' order by event_date";
		 return getEventList(query, connection);
	}
	
	public static Object getAllUpcomingEvents(Connection connection) throws SQLException {
		String query = "select * from EVENT where event_date >= '"+getCurrentDate()+"' order by event_date";
		return getEventList(query, connection);
	}
	
	public static Object getEventByID(String eventId, Connection connection) throws SQLException {
		String query = "select * from EVENT where event_id = '"+eventId+"'";
		return getEvent(query, connection);
	}
	
	private static Date getCurrentDate() {
		java.util.Date currentDate = new java.util.Date();
		Date currentSqlDate = new Date(currentDate.getTime());
		return currentSqlDate;
	}
}