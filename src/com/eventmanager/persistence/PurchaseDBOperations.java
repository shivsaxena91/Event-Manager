package com.eventmanager.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.eventmanager.events.Event;
import com.eventmanager.events.EventTicket;
import com.eventmanager.users.User;
import com.eventmanager.utilities.Constants;

public class PurchaseDBOperations {
	public static boolean purchaseTickets(EventTicket ticket, Connection connection) throws SQLException {
		boolean purchaseStatus = true;
		
		PreparedStatement preparedStatement = null;
		 try {
		      String query = "insert into EVENT_TICKET(ticket_id, user_id, event_id, number_of_tickets, total_cost, purchase_time) values(?,?,?,?,?,?)";
		      preparedStatement = connection.prepareStatement(query);
		      
		      preparedStatement.setString(1, ticket.getTicketId());
		      preparedStatement.setString(2, ticket.getUser().getUserId());
		      preparedStatement.setString(3, ticket.getEvent().getEventId());
		      preparedStatement.setInt(4, ticket.getNumberOfTickets());
		      preparedStatement.setDouble(5, ticket.getTotalCost());
		      preparedStatement.setTimestamp(6, ticket.getPurchaseTime());
		      
		      preparedStatement.executeUpdate();
		 } 
		 catch (SQLException sqle) {
			 purchaseStatus = false;
		 }
		 finally {
			 if(preparedStatement!=null){
				 preparedStatement.close(); 
			 }
		}
		return purchaseStatus;
	}
	
	public static Object getTickets(User user, Connection connection) throws SQLException {
		List<EventTicket> ticketList = new ArrayList<>();
		Statement statement = null;
	    ResultSet resultSet = null;
	   
		try {
			String query="select * from EVENT_TICKET where user_id = '"+user.getUserId()+"' order by purchase_time desc";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	        	String ticketId = resultSet.getString("ticket_id");
	        	String eventId = resultSet.getString("event_id");
	        	int numberOfTickets = resultSet.getInt("number_of_tickets");
	        	double totalCost = resultSet.getDouble("total_cost");
	        	Timestamp purchaseTime = resultSet.getTimestamp("purchase_time");
	        	
	        	Event event = (Event) EventDBOperations.getEventByID(eventId, connection);
	        	
	        	ticketList.add(new EventTicket(ticketId, user, event, numberOfTickets, totalCost, purchaseTime));
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
		return ticketList;
	}
}
