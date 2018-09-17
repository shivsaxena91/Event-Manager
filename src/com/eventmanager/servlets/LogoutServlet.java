package com.eventmanager.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventmanager.persistence.DBUtils;
import com.eventmanager.utilities.Constants;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logOut(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) request.getSession().getAttribute(Constants.DB_CONNECTION);
		if(connection != null) {
			DBUtils.closeDBConnection(connection);
		}
		clearUnwantedSessionAttributes(request, response);
		
		request.getRequestDispatcher(Constants.LOGIN_JSP).forward(request, response);
	}
	
	private void clearUnwantedSessionAttributes(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constants.DB_CONNECTION, null);
		request.getSession().setAttribute(Constants.LOGGED_IN_USER, null);
		request.getSession().setAttribute(Constants.SIGN_UP_STATUS, null);
		request.getSession().setAttribute(Constants.RESET_PASSWORD_STATUS, null);
		request.getSession().setAttribute(Constants.EVENT_CREATION_STATUS, null);
		request.getSession().setAttribute(Constants.EVENT_BOOKING_STATUS, null);
		
		request.getSession().setAttribute(Constants.USER_HOMEPAGE, null);
	}
}