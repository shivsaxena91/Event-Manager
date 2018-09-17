<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String signUpError = (String)request.getSession().getAttribute(Constants.SIGN_UP_STATUS);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			clearErrors();
			<%if(signUpError!=null && !signUpError.equals("")){%>
		   		$("#errorMessage").html('<%=signUpError%>');
		   <%}%>
		});
		function validateForm(){
			var errorFlag = false;
			var firstName = $("#firstName").val();
			var lastName = $("#lastName").val();
			var emailAddress = $("#emailAddress").val();
			var userType = $("#userType").val();
			var password = $("#password").val();
			var confirmPassword = $("#confirmPassword").val();
			clearErrors();
			
			if(firstName == ""){
				errorFlag = true;
				$("#fNameError").html("Please, enter your first name.");
			}
			
			if(lastName == ""){
				errorFlag = true;
				$("#lNameError").html("Please, enter your last name.");
			}
			
			if(emailAddress == ""){
				errorFlag = true;
				$("#emailError").html("Please, enter your email address.");
			}
			else if(!emailAddress.includes("@") || !emailAddress.includes(".")){
				errorFlag = true;
				$("#emailError").html("Please, enter a valid email address.");
			}
			
			if(!$('#organizer').is(':checked') && !$('#participant').is(':checked')){
				errorFlag = true;
				$("#typeError").html("Please, select user type.");
			}
			
			if(password == ""){
				errorFlag = true;
				$("#passwordError").html("Please, enter password.");
			}
			else if(password.length != 8){
				errorFlag = true;
				$("#passwordError").html("Password should have 8 characters.");
			}
			
			if(confirmPassword == ""){
				errorFlag = true;
				$("#confirmPasswordError").html("Please, confirm your password.");
			}
			else if(confirmPassword.length != 8){
				errorFlag = true;
				$("#confirmPasswordError").html("Password should have 8 characters.");
			}
			else if(password != confirmPassword){
				errorFlag = true;
				$("#confirmPasswordError").html("Confirm password should be same as password.");
			}
			
			if(!errorFlag){
				$("#toDo").val("signup");
				$("#signupForm").submit();
			}
		}
		
		function clearErrors(){
			$("#lNameError").html("");
			$("#fNameError").html("");
			$("#emailError").html("");
			$("#typeError").html("");
			$("#passwordError").html("");
			$("#confirmPasswordError").html("");
		}
	</script>
<title>Sign-Up</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form action="/event_manager/homepage" method="post" id="homePageForm">
		<input type="submit" id="backButton" class="backButton" value="Back To Login">
</form>
	<center>
		<h1>Sign-Up</h1>
		<form id="signupForm" action="/event_manager/register" method="post">
			<table>
				<tr>
					<td colspan="2" id="errorMessage" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">First Name:</label></td>
					<td><input type="text" name="firstName" id="firstName" maxlength="20"></td>
					<td id="fNameError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Last Name:</label></td>
					<td><input type="text" name="lastName" id="lastName" maxlength="20"></td>
					<td id="lNameError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Email Address:</label></td>
					<td><input type="text" name="emailAddress" id="emailAddress" maxlength="120"></td>
					<td id="emailError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">User Type:</label></td>
					<td>
						<input type="radio" id="organizer" name="userType" value="organizer"> Event Organizer
					</td>
					<td id="typeError" style="color: red"></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="radio" id="participant" name="userType" value="participant"> Event Participant
					</td>
					<td></td>
				</tr>
				
				<tr>
					<td><label class="bold">Password:</label></td>
					<td><input type="password" name="password" id="password" maxlength="8"></td>
					<td id="passwordError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Confirm Password:</label></td>
					<td><input type="password" name="confirmPassword" id="confirmPassword" maxlength="8"></td>
					<td id="confirmPasswordError" style="color: red"></td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="2"><input type="button" class="button" value="Register" id="register" onclick="validateForm()"></td>
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