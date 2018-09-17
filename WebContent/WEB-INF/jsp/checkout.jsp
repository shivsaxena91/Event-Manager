<%@page import="com.eventmanager.events.EventTicket"%>
<%@page import="com.eventmanager.utilities.Constants"%>
<%@page import="com.eventmanager.events.Event"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
EventTicket eventTicket = (EventTicket)request.getSession().getAttribute(Constants.SELECTED_TICKET);
Event event = eventTicket.getEvent();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
			$("#nameError").html("");
			$("#cardNumberError").html("");
			$("#monthError").html("");
			$("#yearError").html("");
			$("#cvvError").html("");
		});
		
		function validateForm(){
			var errorFlag = false;
			var nameOnCard = $("#nameOnCard").val();
			var cardNumber = $("#cardNumber").val();
			var month = $("#month").val();
			var year = $("#year").val();
			var cvv = $("#cvv").val();
			
			$("#nameError").html("");
			$("#cardNumberError").html("");
			$("#monthError").html("");
			$("#yearError").html("");
			$("#cvvError").html("");
			var alphabets = /^[A-Za-z ]+$/;
			var numbers = /^[0-9]+$/;
			
			if(nameOnCard == ""){
				errorFlag = true;
				$("#nameError").html("Please, enter the name on your debit/credit card.");
			}
			else if(!nameOnCard.match(alphabets)){
				errorFlag = true;
				$("#nameError").html("Please, enter alphabets only.");
			}
			if(cardNumber == ""){
				errorFlag = true;
				$("#cardNumberError").html("Please, enter card number.");
			}
			else if(!cardNumber.match(numbers)){
				errorFlag = true;
				$("#cardNumberError").html("Please, enter numbers only.");
			}
			else if(cardNumber.length != 16 || cardNumber < 1000000000000000){
				errorFlag = true;
				$("#cardNumberError").html("Please, enter a valid credit/debit card number.");
			}
			
			if(month == "selectMonth"){
				$("#monthError").html("Please, select a valid month.");
			}
			
			if(year == "selectYear"){
				$("#yearError").html("Please, select a valid year.");
			}
			
			if(cvv == ""){
				errorFlag = true;
				$("#cvvError").html("Please, enter CVV.");
			}
			else if(!cvv.match(numbers)){
				errorFlag = true;
				$("#cvvError").html("Please, enter numbers only.");
			}
			else if(cvv.length != 3 || cvv < 10){
				errorFlag = true;
				$("#cvvError").html("Please, enter a valid CVV number.");
			}
			
			if(!errorFlag){
				$("#toDo").val("purchaseTickets");
				$("#checkoutForm").submit();
			}
		}
	
		function backToEventListing(){
			$("#eventListingForm").submit();
		}
	</script>
<title>Checkout</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
</form>
<form action="/event_manager/homepage" method="post" id="eventListingForm">
			<input type="hidden" id="action" name="toDo" value="backToEventListing">
			<input type="button" class="backButton" id="backButton" value="Back To Event Listing" onclick="backToEventListing()">
</form>
<center>
		<h1>Checkout</h1>
		<form id="checkoutForm" action="/event_manager/purchaseTickets" method="post">
			<table>
				<tr>
					<td colspan="2" id="errorMessage" style="color: red"></td>
					<td></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label style="font-weight: bold;">Event Name:</label></td>
					<td><%=event.getEventName()%></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label style="font-weight: bold;">Number of Tickets:</label></td>
					<td><%=eventTicket.getNumberOfTickets()%></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label style="font-weight: bold;">Total Amount:</label></td>
					<td>$ <%=eventTicket.getTotalCost()%></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label style="font-weight: bold;">Event Date:</label></td>
					<td><%=event.getEventDate()%></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label style="font-weight: bold;">Event Time:</label></td>
					<td><%=event.getEventTime()%></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label style="font-weight: bold;">Event Location:</label></td>
					<td><%=event.getEventLocation()%></td>
					<td></td>
				</tr>
				
				<tr>
					<td><label>Name on Card:</label></td>
					<td><input type="text" name="nameOnCard" id="nameOnCard" maxlength="50"></td>
					<td id="nameError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label>Card Number</label></td>
					<td><input type="text" name="cardNumber" id="cardNumber" maxlength="16"></td>
					<td id="cardNumberError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label>Valid Till:</label></td>
					<td>
						<select id="month" name="month">
							<option value="selectMonth">Month</option>
							<option value="1">January</option>
							<option value="2">February</option>
							<option value="3">March</option>
							<option value="4">April</option>
							<option value="5">May</option>
							<option value="6">June</option>
							<option value="7">July</option>
							<option value="8">August</option>
							<option value="9">September</option>
							<option value="10">October</option>
							<option value="11">November</option>
							<option value="12">December</option>
						</select>
					</td>
					<td id="monthError" style="color: red"></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<select id="year" name="year">
							<option value="selectYear">Year</option>
							<option value="1">18</option>
							<option value="2">19</option>
							<option value="3">20</option>
							<option value="4">21</option>
							<option value="5">22</option>
							<option value="6">23</option>
							<option value="7">24</option>
							<option value="8">25</option>
							<option value="9">26</option>
							<option value="10">27</option>
							<option value="11">28</option>
							<option value="12">29</option>
							<option value="13">30</option>
						</select>
					</td>
					<td id="yearError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label>CVV</label></td>
					<td><input type="password" name="cvv" id="cvv" size="5" maxlength="3"></td>
					<td id="cvvError" style="color: red"></td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="2"><input class="button" type="button" value="Confirm Payment" onclick="validateForm()"></td>
				</tr>
				
				<tr>
					<td><input type="hidden" id="numberOfTickets" name="numberOfTickets" value="<%=eventTicket.getNumberOfTickets()%>"></td>
					<td><input type="hidden" id="eventId" name="eventId" value="<%=event.getEventId()%>"></td>
					<td><input type="hidden" id="toDo" name="toDo"></td>
				</tr>
			</table>
		</form>
		<br><br>
	</center>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>