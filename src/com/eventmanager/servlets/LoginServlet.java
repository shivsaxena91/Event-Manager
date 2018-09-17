package com.eventmanager.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventmanager.persistence.DBUtils;
import com.eventmanager.users.EventParticipant;
import com.eventmanager.users.User;
import com.eventmanager.users.UserType;
import com.eventmanager.utilities.Constants;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		clearUnwantedSessionAttributes(request, response);
		checkIfUserExists(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void checkIfUserExists(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		connection = DBUtils.openDBConnection();
		request.getSession().setAttribute(Constants.DB_CONNECTION, connection);
		
		String emailAddress = request.getParameter("username");
		String password = request.getParameter("password");
		User signInUser = new EventParticipant();
		
		Object[] resultStatus = signInUser.getUserByCredentials(emailAddress, password, connection);
		if(resultStatus.length == 3) {
			User user = (User)resultStatus[2];
			request.getSession().setAttribute(Constants.LOGGED_IN_USER, user);
			if(user.getUserType() == UserType.EVENT_ORGANIZER) {
				request.getSession().setAttribute(Constants.USER_HOMEPAGE, Constants.ORGANIZER_HOMEPAGE_JSP);
			}
			else {
				request.getSession().setAttribute(Constants.USER_HOMEPAGE, Constants.PARTICIPANT_HOMEPAGE_JSP);
			}
		}
		
		request.getSession().setAttribute(Constants.SIGN_UP_STATUS, (String)resultStatus[0]);
		request.getRequestDispatcher((String)resultStatus[1]).forward(request, response);
	}
	
	private void clearUnwantedSessionAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, null);
		request.getSession().setAttribute(Constants.SIGN_UP_STATUS, null);
	}
}