<%@page import="com.eventmanager.users.User"%>
<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String passwordResetStatus = (String)request.getSession().getAttribute(Constants.RESET_PASSWORD_STATUS);
String eventBookingStatus = (String)request.getSession().getAttribute(Constants.EVENT_BOOKING_STATUS);
User user = (User)request.getSession().getAttribute(Constants.LOGGED_IN_USER);
String errorMessage = null;
String successMessage = null;
if(eventBookingStatus != null){
	if(eventBookingStatus.contains("successful")){
		successMessage = eventBookingStatus;
	}
	else{
		errorMessage = eventBookingStatus;
	}
}
if(passwordResetStatus != null){
	successMessage = passwordResetStatus;
}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		<%if(successMessage!=null && !successMessage.equals("")){%>
			$("#successMessage").html('<%=successMessage%>');
		<%}
		else{%>
			$("#successMessage").html("");
		<%}%>
		
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
	
	function goToPage(page){
		$("#successMessage").html("");
		$("#errorMessage").html("");
		<%
		successMessage = null;
		errorMessage = null;
		request.getSession().setAttribute(Constants.EVENT_BOOKING_STATUS, null);
		%>
		$("#toDo").val(page)
		$("#navigationForm").submit();
	}
</script>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
	</form>
	<center>
		<h1>Participant Homepage</h1>
		<h5>Welcome <%=user.getFirstName() %> !!</h5>
		<form action="/event_manager/eventParticipant" method="post" id="navigationForm">
			<table>
				<tr>
					<td id="successMessage" style="color: green"></td>
				</tr>
				<tr>
					<td id="errorMessage" style="color: red"></td>
				</tr>
				<tr>
					<td><input type="button" class="button" id="exploreUpcomingEvents" value="Explore Upcoming Events" onclick="goToPage('exploreUpcomingEvents')"></td>
				</tr>
				
				<tr>
					<td><input type="button" class="button" id="viewPurchaseHistory" value="View Purchase History" onclick="goToPage('viewPurchaseHistory')"></td>
				</tr>
				
				<tr>
					<td><input type="button" class="button" id="resetPassword" value="Reset Password" onclick="goToPage('navigateToResetPasswordPage')"></td>
				</tr>
				
				<tr>
					<td><input type="hidden" id="toDo" name="toDo"></td>
				</tr>
			</table>
		</form>
	</center>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>