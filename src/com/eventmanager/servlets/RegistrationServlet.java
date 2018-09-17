package com.eventmanager.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventmanager.persistence.DBUtils;
import com.eventmanager.users.EventOrganizer;
import com.eventmanager.users.EventParticipant;
import com.eventmanager.users.OrganizerFactory;
import com.eventmanager.users.ParticipantFactory;
import com.eventmanager.users.User;
import com.eventmanager.users.UserAbstractFactory;
import com.eventmanager.users.UserFactory;
import com.eventmanager.users.UserType;
import com.eventmanager.utilities.Constants;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		clearUnwantedSessionAttributes(request, response);
		String toDo = (String)request.getParameter(Constants.TO_DO);
		
		if(toDo != null) {
			switch (toDo) {
			case Constants.NAVIGATE_TO_SIGN_UP_PAGE:
				request.getRequestDispatcher(Constants.REGISTRATION_JSP).forward(request, response);
				break;
			
			case Constants.SIGN_UP:
				createNewUser(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void createNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = (String)request.getParameter("firstName");
		String lastName = (String)request.getParameter("lastName");
		String emailAddress = (String)request.getParameter("emailAddress");
		String userType = (String)request.getParameter("userType");
		String password = (String)request.getParameter("password");
		UserAbstractFactory user = null;
		
		if(userType != null) {
			switch (userType) {
			case Constants.EVENT_ORGANIZER:
				user = new OrganizerFactory(firstName, lastName, UserType.EVENT_ORGANIZER, emailAddress, password);
				break;
				
			case Constants.EVENT_PARTICIPANT:
				user = new ParticipantFactory(firstName, lastName, UserType.EVENT_PARTICIPANT, emailAddress, password);
				break;

			default:
				break;
			}
		}
		
		Connection connection = DBUtils.openDBConnection();
		String[] resultStatus = UserFactory.getUser(user, connection);
		DBUtils.closeDBConnection(connection);
		request.getSession().setAttribute(Constants.SIGN_UP_STATUS, resultStatus[0]);
		request.getRequestDispatcher(resultStatus[1]).forward(request, response);
	}
	
	private void clearUnwantedSessionAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constants.SIGN_UP_STATUS, null);
		request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, null);
	}
}
