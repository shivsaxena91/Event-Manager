package com.eventmanager.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventmanager.events.Event;
import com.eventmanager.events.EventTicket;
import com.eventmanager.users.User;
import com.eventmanager.utilities.Constants;

/**
 * Servlet implementation class PurchaseTicketsServlet
 */
@WebServlet("/PurchaseTicketsServlet")
public class PurchaseTicketsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseTicketsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		clearUnwantedSessionAttributes(request, response);
		String toDo = request.getParameter("toDo");
		
		if(toDo != null) {
			switch (toDo) {
			case Constants.NAVIGATE_TO_CHECKOUT_PAGE:
				navigateToCheckoutPage(request, response);
				break;
			case Constants.PURCHASE_TICKETS:
				purchaseTickets(request, response);
				break;

			default:
				break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void navigateToCheckoutPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EventTicket ticket = getEventTicket(request, response);
		request.getSession().setAttribute(Constants.SELECTED_TICKET, ticket);
		request.getRequestDispatcher(Constants.CHECKOUT_JSP).forward(request, response);
	}
	
	private void purchaseTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EventTicket ticket = getEventTicket(request, response);
        
        Connection connection = (Connection)request.getSession().getAttribute(Constants.DB_CONNECTION);
        if(ticket.purchaseTickets(connection)) {
        	request.getSession().setAttribute(Constants.EVENT_BOOKING_STATUS, Constants.PURCHASE_SUCCESS);
        }
        else {
        	request.getSession().setAttribute(Constants.EVENT_BOOKING_STATUS, Constants.DB_ERROR);
        }
        request.getRequestDispatcher(Constants.PARTICIPANT_HOMEPAGE_JSP).forward(request, response);
	}
	
	private EventTicket getEventTicket(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Event> eventsMap = (HashMap<String, Event>) request.getSession().getAttribute(Constants.UPCOMING_EVENTS_MAP);
		String eventId = request.getParameter("eventId");
		
		Event event = eventsMap.get(eventId);
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USER);
		int numberOfTickets = Integer.valueOf(request.getParameter("numberOfTickets"));
		
		double totalCost = numberOfTickets * event.getTicketPrice();
		totalCost = totalCost*102.0/100.0;
		
		Date currentDate = new Date();
        Timestamp purchaseTime = new Timestamp(currentDate.getTime());
        
		return new EventTicket(user, event, numberOfTickets, totalCost, purchaseTime);
	}
	
	private void clearUnwantedSessionAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constants.EVENT_BOOKING_STATUS, null);
	}
}
