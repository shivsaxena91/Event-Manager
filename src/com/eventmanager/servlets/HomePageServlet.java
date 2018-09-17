package com.eventmanager.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventmanager.utilities.Constants;

/**
 * Servlet implementation class HomePageServlet
 */
@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePageServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toDo = request.getParameter("toDo");
		if(toDo == null) {
			request.getRequestDispatcher(Constants.LOGIN_JSP).forward(request, response);
		}
		else if(toDo.equals(Constants.BACK_TO_HOMEPAGE)){
			String homePage = (String) request.getSession().getAttribute(Constants.USER_HOMEPAGE);
			request.getRequestDispatcher(homePage).forward(request, response);
		}
		else if(toDo.equals(Constants.BACK_TO_EVENTLISTING)) {
			request.getRequestDispatcher(Constants.EVENT_LISTING_JSP).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
