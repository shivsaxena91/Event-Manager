<%@page import="java.util.ArrayList"%>
<%@page import="com.eventmanager.events.Event"%>
<%@page import="java.util.List"%>
<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String noEventsMessage = "";
String searchKeyword = (String)request.getSession().getAttribute(Constants.SEARCH_KEYWORD);
String errorMessage = (String)request.getSession().getAttribute(Constants.UPCOMING_EVENTS_STATUS);
ArrayList<Event> eventList = (ArrayList<Event>)request.getSession().getAttribute(Constants.UPCOMING_EVENTS);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event Listing</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	<%if(searchKeyword == null){%>
		$("#keyword").val("");
	<%if(eventList.size() == 0){
			noEventsMessage = Constants.NO_UPCOMING_EVENTS;
		}
	}
	else{%>
		$("#keyword").val('<%=searchKeyword%>');
	<%	if(eventList.size() == 0){
			noEventsMessage = Constants.NO_MATCHING_EVENTS;
		}
	}%>
});
function logoutFromEventManager() {
	$("#logoutForm").submit();
}

function navigateToCheckoutPage(formNumber, formName, eventId){
	$("#eventId"+formNumber).val(eventId);
	$("#toDo"+formNumber).val("navigateToCheckoutPage");
	$("#"+formName).submit();
}

function performAction(action){
	if(action == "searchEvents"){
		$("#searchError").html("");
		var keyword = $("#keyword").val();
		if(keyword == ""){
			$("#searchError").html('<%=Constants.SEARCH_ERROR %>');
		}
		else{
			$("#searchOperation").val("searchEvents");
			$("#searchEventForm").submit();
		}
	}
	else if(action == "closeSearch"){
		$("#searchError").html("");
		$("#searchOperation").val("closeSearch");
		$("#searchEventForm").submit();
	}
}
</script>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
	</form>
	<jsp:include page="backButton.jsp"/>
	<center>
	<%	if(searchKeyword != null || eventList != null){%>
			<form action="/event_manager/eventParticipant" method="post" id="searchEventForm">
				<section style="margin: 10px;">
				<fieldset style="min-height:100px;">
					<legend>Search Events</legend>
					<table>
						<tr>
							<td>
								<input type="text" id="keyword" name="keyword">
							</td>
							<td id="searchError" style="color: red"></td>
						</tr>
						<tr>
							<td>
								<input type="button" class="button" id="searchEvent" value="Search Event" onclick="performAction('searchEvents')">
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="blackButton" id="closeSearch" value="Close Search" onclick="performAction('closeSearch')">
							</td>
						</tr>
					</table>
					<input type="hidden" id="searchOperation" name="toDo">
				</fieldset>
				</form>
				<br><br>
				<h1><%=Constants.EVENT_LIST%></h1>
		<%}
		if(eventList != null){
			if(eventList.size() == 0){
				%><h1><%=noEventsMessage%></h1><%
			}
			else{
				int formNumber = 0;%>
				<section style="margin: 10px;">
				<%
				for(Event event: eventList){
				++formNumber;
				%>
				<form action="/event_manager/purchaseTickets" method="post" id="purchaseTicketsForm<%=formNumber%>">
					<fieldset style="min-height:100px;">
						<legend>Event Name: <%=event.getEventName() %></legend>
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
							<%if(event.getAvailableTickets() <= 0){
								%>
								<tr>
									<td colspan="1"><h2 style="color: red">All tickets are sold.</h2></td>
								</tr>
								<%
							}
							else{%>
								<tr>
									<td style="font-weight: bold;">Select Number of Tickets:</td>
									<td>
										<select id="numberOfTickets" name="numberOfTickets">
											<%
											for(int index = 1; index <= event.getAvailableTickets(); index++){
												%><option value="<%=index%>"><%=index%></option><%
											}
											%>
										</select> 
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="button" class="button" value="Purchase Tickets" onclick="navigateToCheckoutPage('<%=formNumber%>', 'purchaseTicketsForm<%=formNumber%>', '<%=event.getEventId()%>')">
									</td>
								</tr>
							<%}%>
						</table>
					</fieldset>
					<br><br>
					<input type="hidden" id="eventId<%=formNumber%>" name="eventId">
					<input type="hidden" id="toDo<%=formNumber%>" name="toDo">
				</form>
				<%}
			}
		}
		else{
			%><h2><%=Constants.DB_ERROR%></h2><%
		}
		%>
	</center>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>