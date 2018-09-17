package com.eventmanager.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventmanager.events.Event;
import com.eventmanager.users.User;
import com.eventmanager.utilities.Constants;

/**
 * Servlet implementation class EventManagerServlet
 */
@WebServlet("/EventManagerServlet")
public class EventManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventManagerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		clearUnwantedSessionAttributes(request, response);
		String toDo = (String)request.getParameter(Constants.TO_DO);
		
		if(toDo != null) {
			switch (toDo) {
			case Constants.NAVIGATE_TO_CREATE_NEW_EVENT_PAGE:
				request.getRequestDispatcher(Constants.CREATE_NEW_EVENT_JSP).forward(request, response);
				break; 
			case Constants.CREATE_NEW_EVENT:
				createNewEvent(request, response);
				break;
			case Constants.CHECK_EVENT_STATUS:
				checkEventStatus(request, response);
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
	
	private void createNewEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		User loggedInUser = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USER);
		
		String eventName = request.getParameter("eventName");
		String eventDescription = request.getParameter("eventDescription");
		String eventLocation = request.getParameter("eventLocation");
		
		DateFormat format = new java.text.SimpleDateFormat("yyy-MM-dd");
		java.util.Date date = null;
		try {
			date = format.parse((String)request.getParameter("eventDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date eventDate = new Date(date.getTime());
		
		String eventTime = request.getParameter("eventTime");
		
		double ticketPrice = Double.valueOf(request.getParameter("ticketPrice"));
		int eventCapacity = Integer.valueOf(request.getParameter("eventCapacity"));
		
		Event event = new Event(eventName, eventDescription, eventLocation, eventDate, eventTime, ticketPrice, eventCapacity, loggedInUser);
		Connection connection = (Connection)request.getSession().getAttribute(Constants.DB_CONNECTION);
		
		String[] resultStatus = event.createNewEvent(loggedInUser, connection);
		request.getSession().setAttribute(Constants.EVENT_CREATION_STATUS, resultStatus[0]);
		request.getRequestDispatcher(resultStatus[1]).forward(request, response);
	}
	
	private void checkEventStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USER);
		Connection connection = (Connection)request.getSession().getAttribute(Constants.DB_CONNECTION);
		ArrayList<Event> eventList = Event.getEventListByOrganizerId(loggedInUser.getUserId(), connection);
		
		request.getSession().setAttribute(Constants.EVENT_STATUS_LIST, eventList);
		request.getRequestDispatcher(Constants.EVENT_STATUS_JSP).forward(request, response);
	}
	
	private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(Constants.RESET_PASSWORD_JSP).forward(request, response);
	}
	
	private void clearUnwantedSessionAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, null);
		request.getSession().setAttribute(Constants.EVENT_CREATION_STATUS, null);
	}
}
