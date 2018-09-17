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
 * Servlet implementation class PasswordResetServlet
 */
@WebServlet("/PasswordResetServlet")
public class PasswordResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordResetServlet() {
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
			case Constants.FORGOT_PASSWORD:
				forgotPassword(request, response);
				break;
			case Constants.RESET_PASSWORD:
				resetPassword(request, response);
				break;
			case Constants.NAVIGATE_TO_FORGOT_PASSWORD_JSP:
				request.getRequestDispatcher(Constants.FORGOT_PASSWORD_JSP).forward(request, response);
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
	
	private void forgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddress = request.getParameter("emailAddress");
		
		Connection connection = DBUtils.openDBConnection();
		User user = new EventParticipant();
		
		user.forgotPassword(emailAddress, connection);
		DBUtils.closeDBConnection(connection);
		
		request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, Constants.CREDENTIALS_EMAILED);
		request.getRequestDispatcher(Constants.LOGIN_JSP).forward(request, response);
	}
	
	private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		
		User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USER);
		Connection connection = (Connection)request.getSession().getAttribute(Constants.DB_CONNECTION);
		
		if(user.resetPassword(currentPassword, newPassword, connection)) {
			request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, Constants.PASSWORD_RESET_SUCCESSFUL);
			if(user.getUserType() == UserType.EVENT_ORGANIZER)
				request.getRequestDispatcher(Constants.ORGANIZER_HOMEPAGE_JSP).forward(request, response);
			else
				request.getRequestDispatcher(Constants.PARTICIPANT_HOMEPAGE_JSP).forward(request, response);
		}
		else {
			request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, Constants.INCORRECT_CURRENT_PASSWORD);
			request.getRequestDispatcher(Constants.RESET_PASSWORD_JSP).forward(request, response);
		}
	}
	
	private void clearUnwantedSessionAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, null);
		request.getSession().setAttribute(Constants.SIGN_UP_STATUS, null);
	}
}
