package com.eventmanager.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
 * Servlet implementation class EventParticipantServlet
 */
@WebServlet("/EventParticipantServlet")
public class EventParticipantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventParticipantServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toDo = (String)request.getParameter(Constants.TO_DO);
		request.getSession().setAttribute(Constants.EVENT_BOOKING_STATUS, null);
		
		if(toDo != null) {
			switch (toDo) {
			case Constants.EXPLORE_UPCOMING_EVENTS:
				getUpcomingEvents(request, response);
				break; 
			case Constants.VIEW_PURCHASE_HISTORY:
				getPurchaseHistory(request, response);
				break; 
			case Constants.SEARCH_EVENTS:
				searchEvents(request, response);
				break;
			case Constants.CLOSE_SEARCH:
				getUpcomingEvents(request, response);
				break;
			case Constants.NAVIGATE_TO_RESET_PASSWORD_JSP:
				resetPassword(request, response);
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
	
	private void getPurchaseHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USER);
		Connection connection = (Connection)request.getSession().getAttribute(Constants.DB_CONNECTION);
		ArrayList<EventTicket> ticketList = EventTicket.getTickets(loggedInUser, connection);
		
		request.getSession().setAttribute(Constants.PURCHASE_HISTORY_LIST, ticketList);
		request.getRequestDispatcher(Constants.PURCHASE_HISTORY_JSP).forward(request, response);
	}
	
	private void getEvents(HttpServletRequest request, HttpServletResponse response, String keyword) throws ServletException, IOException {
		Connection connection = (Connection)request.getSession().getAttribute(Constants.DB_CONNECTION);
		ArrayList<Event> eventList = null;
		if(keyword == null) {
			request.getSession().setAttribute(Constants.SEARCH_KEYWORD, null);
			eventList = Event.getUpcomingEvents(connection);
		}
		else {
			request.getSession().setAttribute(Constants.SEARCH_KEYWORD, keyword);
			eventList = Event.searchEvents(keyword, connection);
		}
		if(eventList == null) {
			if(keyword == null) {
				request.getSession().setAttribute(Constants.UPCOMING_EVENTS_STATUS, Constants.DB_ERROR);
				request.getSession().setAttribute(Constants.UPCOMING_EVENTS, null);
			}
			else {
				request.getSession().setAttribute(Constants.UPCOMING_EVENTS_STATUS, Constants.DB_ERROR);
				request.getSession().setAttribute(Constants.UPCOMING_EVENTS, null);
			}
		}
		else {
			Map<String, Event> eventMap = new HashMap<>();
			for(Event event:eventList) {
				eventMap.put(event.getEventId(), event);
			}
			request.getSession().setAttribute(Constants.UPCOMING_EVENTS_STATUS, null);
			request.getSession().setAttribute(Constants.UPCOMING_EVENTS, eventList);
			request.getSession().setAttribute(Constants.UPCOMING_EVENTS_MAP, eventMap);
		}
		request.getRequestDispatcher(Constants.EVENT_LISTING_JSP).forward(request, response);
	}
	
	private void searchEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		getEvents(request, response, keyword);
	}
	
	private void getUpcomingEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getEvents(request, response, null);
	}
	
	private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(Constants.RESET_PASSWORD_JSP).forward(request, response);
	}
}
