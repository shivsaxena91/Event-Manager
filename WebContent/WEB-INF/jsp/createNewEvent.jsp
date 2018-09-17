<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String eventCreationStatus = (String)request.getSession().getAttribute(Constants.EVENT_CREATION_STATUS);
String errorMessage = null;
if(eventCreationStatus != null){
	if(!eventCreationStatus.contains("successful")){
		errorMessage = eventCreationStatus;
	}
}
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
	clearErrors();
	<%if(errorMessage!=null && !errorMessage.equals("")){%>
		$("#errorMessage").html('<%=errorMessage%>');
	<%}
	else{%>
		$("#errorMessage").html("");
	<%}%>
});

function logoutFromEventManager() {
	$("#logoutForm").submit();
}

function createNewEvent() {
	var errorFlag = false;
	var eventName = $("#eventName").val()
	var eventDescription = $("#eventDescription").val()
	var eventLocation = $("#eventLocation").val()
	var eventDate = $("#eventDate").val()
	var ticketPrice = $("#ticketPrice").val()
	var eventCapacity = $("#eventCapacity").val()
	var numbers = /^[0-9]+$/;
	
	if(eventName == ""){
		errorFlag = true;
		$("#nameError").html("Please, enter a name for event.");
	}
	else{
		$("#nameError").html("");
	}
	
	if(eventName == ""){
		errorFlag = true;
		$("#nameError").html("Please, enter a name for event.");
	}
	else{
		$("#nameError").html("");
	}
	
	if(eventDescription == ""){
		errorFlag = true;
		$("#descriptionError").html("Please, enter a description for event.");
	}
	else{
		$("#descriptionError").html("");
	}
	
	if(eventLocation == ""){
		errorFlag = true;
		$("#locationError").html("Please, enter a location for event.");
	}
	else{
		$("#locationError").html("");
	}
	
	if(eventDate == ""){
		errorFlag = true;
		$("#dateError").html("Please, enter a date for event.");
	}
	else{
		$("#dateError").html("");
	}
	
	if(ticketPrice == ""){
		errorFlag = true;
		$("#priceError").html("Please, enter a ticket price.");
	}
	else if(!ticketPrice.match(numbers)){
		errorFlag = true;
		$("#priceError").html("Please, enter numbers only.");
	}
	else{
		$("#priceError").html("");
	}
	
	if(eventCapacity == ""){
		errorFlag = true;
		$("#capacityError").html("Please, enter event capacity.");
	}
	else if(!eventCapacity.match(numbers)){
		errorFlag = true;
		$("#capacityError").html("Please, enter numbers only.");
	}
	else{
		$("#capacityError").html("");
	}
	
	var time1 = $("#startTime").val();
	var time2 = $("#endTime").val();
	
	var [hours1,minutes1] = time1.split(':');
	var [hours2,minutes2] = time2.split(':');
	
	if(minutes1 == null || minutes2 == null){
		errorFlag == true;
		$("#timeError").html("Please, select valid time.");
	}
	else{
		$("#timeError").html("");
		var maridian1 = hours1 >= 12 ? "PM" : "AM";
		var maridian2 = hours2 >= 12 ? "PM" : "AM";
		
		hours1 = hours1 % 12 + 12*(hours1%12 == 0);
		hours2 = hours2 % 12 + 12*(hours2%12 == 0);
		
		var eventTime = hours1 + ":" + minutes1 + " " + maridian1;
		eventTime = eventTime + " to " + hours2 + ":" + minutes2 + " " + maridian2;
		
		$("#eventTime").val(eventTime);
	}
	
	if(!errorFlag){
		$("#toDo").val("createNewEvent");
		$("#eventCreationForm").submit();
	}
}

function clearErrors(){
	$("#nameError").html("");
	$("#descriptionError").html("");
	$("#locationError").html("");
	$("#dateError").html("");
	$("#timeError").html("");
	$("#priceError").html("");
	$("#capacityError").html("");
}
</script>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
	</form>
	<jsp:include page="backButton.jsp"/>
	<center>
		<h1>Create New Event</h1>
		<form action="/event_manager/eventManager" method="post" id="eventCreationForm">
		<table>
				<tr>
					<td colspan="2" id="errorMessage" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Event Name:</label></td>
					<td><input type="text" name="eventName" id="eventName" maxlength="120"></td>
					<td id="nameError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Event Description:</label></td>
					<td><textarea id="eventDescription" name="eventDescription" rows="4" cols="21"></textarea></td>
					<td id="descriptionError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Event Location:</label></td>
					<td><input type="text" name="eventLocation" id="eventLocation" maxlength="200"></td>
					<td id="locationError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Event Date:</label></td>
					<td><input type="date" name="eventDate" id="eventDate"></td>
					<td id="dateError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Event Time:</label></td>
					<td>
						<input type="time" name="startTime" id="startTime"> (From)
						<input type="time" name="endTime" id="endTime"> (To)
					</td>
					<td id="timeError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Ticket Price:</label></td>
					<td><input type="text" name="ticketPrice" id="ticketPrice"></td>
					<td id="priceError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Event Capacity:</label></td>
					<td><input type="text" name="eventCapacity" id="eventCapacity"></td>
					<td id="capacityError" style="color: red"></td>
				</tr>
				
				<tr>
					<td></td>
					<td><input type="button" class="button" value="Create Event" onclick="createNewEvent()"></td>
				</tr>
				
				<tr>
					<td><input type="hidden" id="toDo" name="toDo"></td>
					<td><input type="hidden" id="eventTime" name="eventTime"></td>
				</tr>
			</table>
		</form>
	</center>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>