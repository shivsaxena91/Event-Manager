<%@page import="com.eventmanager.users.User"%>
<%@page import="com.eventmanager.events.EventTicket"%>
<%@page import="com.eventmanager.events.Event"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
ArrayList<EventTicket> ticketList = (ArrayList<EventTicket>)request.getSession().getAttribute(Constants.PURCHASE_HISTORY_LIST);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase History</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

function logoutFromEventManager() {
	$("#logoutForm").submit();
}
</script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
	</form>
	<jsp:include page="backButton.jsp"/>
	<center>
	<%
		if(ticketList != null){
			if(ticketList.size() == 0){
				%><h1><%=Constants.NO_EVENT_HISTORY%></h1><%
			}
			else{
				%><h1><%=Constants.PURCHASE_HISTORY%></h1>
				<section style="margin: 10px;">
				<%
				for(EventTicket eventTicket: ticketList){
					Event event = eventTicket.getEvent();
					User user = eventTicket.getUser();
				%>
					<fieldset style="min-height:100px;">
						<legend style="font-weight: bold;">Event Name: <%=event.getEventName() %></legend>
						<table>
							<tr>
								<td style="font-weight: bold;">Name:</td>
								<td><%=user.getFirstName()+" "+user.getLastName()%></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Number of tickets purchased:</td>
								<td><%=eventTicket.getNumberOfTickets()%></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Total Amount Paid:</td>
								<td><%=eventTicket.getTotalCost() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold;">Purchase Time:</td>
								<td><%=eventTicket.getPurchaseTime() %></td>
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
						</table>
					</fieldset>
					<br><br>
				<%}
			}
		}
		else{
			%><h2><%=Constants.DB_ERROR%></h2><%
		}%>
		</center>
<jsp:include page="footer.jsp"></jsp:include>		
</body>
</html>