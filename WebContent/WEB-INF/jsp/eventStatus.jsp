<%@page import="java.util.ArrayList"%>
<%@page import="com.eventmanager.events.Event"%>
<%@page import="java.util.List"%>
<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
ArrayList<Event> eventList = (ArrayList<Event>)request.getSession().getAttribute(Constants.EVENT_STATUS_LIST);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event Status</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<script type="text/javascript">

function logoutFromEventManager() {
	$("#logoutForm").submit();
}

function navigateToCheckoutPage(formNumber, formName, eventId){
	$("#eventId"+formNumber).val(eventId);
	$("#toDo"+formNumber).val("navigateToCheckoutPage");
	$("#"+formName).submit();
}
</script>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
	</form>
	<jsp:include page="backButton.jsp"/>
	<center>
	<h1><%=Constants.EVENT_STATUS%></h1>
	<%
		if(eventList != null){
			if(eventList.size() == 0){
				%><h1><%=Constants.NO_EVENTS%></h1><%
			}
			else{
				int formNumber = 0;%>
				<section style="margin: 10px;"><%
				for(Event event: eventList){
				++formNumber;
				%>
					
					<fieldset style="min-height:100px;">
						<legend style="font-weight: bold;">Event Name: <%=event.getEventName() %></legend>
						<table>
							<tr>
								<td style="font-weight: bold;">Event Description:</td>
								<td><%=event.getEventDescription() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Event Location:</td>
								<td><%=event.getEventLocation() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Event Date:</td>
								<td><%=event.getEventDate() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Event Time:</td>
								<td><%=event.getEventTime() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Ticket Price:</td>
								<td>$ <%=event.getTicketPrice() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Available Tickets:</td>
								<td><%=event.getAvailableTickets() %></td>
							</tr>
						</table>
					</fieldset>
					<br><br>
				<%}
			}
		}
		else{
			%><h1><%=Constants.DB_ERROR%></h1><%
		}
		%>
		</center>
<jsp:include page="footer.jsp"></jsp:include>		
</body>
</html>