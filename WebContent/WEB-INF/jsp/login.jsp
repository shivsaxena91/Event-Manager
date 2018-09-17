<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String resetPasswordStatus = (String)request.getSession().getAttribute(Constants.RESET_PASSWORD_STATUS);
String signUpStatus = (String)request.getSession().getAttribute(Constants.SIGN_UP_STATUS);
String errorMessage = null;
String successMessage = null;
if(signUpStatus != null){
	if(signUpStatus.contains("successful")){
		successMessage = signUpStatus;
	}
	else{
		errorMessage = signUpStatus;
	}	
}
if(resetPasswordStatus != null){
	successMessage = resetPasswordStatus;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#emailError").html("");
			$("#passwordError").html("");
			
			<%if(errorMessage!=null && !errorMessage.equals("")){%>
				$("#errorMessage").html('<%=errorMessage%>');
			<%}
			else{%>
				$("#errorMessage").html("");
			<%}%>
			
			<%if(successMessage!=null && !successMessage.equals("")){%>
				$("#successMessage").html('<%=successMessage%>');
			<%}
			else{%>
				$("#successMessage").html("");
			<%}%>
		});
		function validateCredentials(){
			var errorFlag = false;
			var username = $("#username").val();
			var password = $("#password").val();
			$("#emailError").html("");
			$("#passwordError").html("");
			
			if(username == ""){
				errorFlag = true;
				$("#emailError").html("Please, enter your email address.");
			}
			else if(!username.includes("@") || !username.includes(".")){
				errorFlag = true;
				$("#emailError").html("Please, enter a valid email address.");
			}
			
			if(password == ""){
				errorFlag = true;
				$("#passwordError").html("Please, enter your password.");
			}
			else if(password.length != 8){
				errorFlag = true;
				$("#passwordError").html("Password should have 8 characters.");
			}
			
			if(!errorFlag){
				$("#loginForm").submit();
			}
		}
		
		function navigateToSignupPage(){
			$("#toDo").val("navigateToSignupPage");
			$("#registrationForm").submit();
		}
		
		function navigateToForgotPasswordPage(){
			$("#forgotPasswordForm").submit();
		}
	</script>
	<title>Login</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<center>
		<h1>Login</h1>
		<form id="loginForm" action="/event_manager/login" method="post">
			<table>
				<tr>
					<td colspan="2" id="errorMessage" style="color: red"></td>
				</tr>
				<tr>
					<td colspan="2" id="successMessage" style="color: green"></td>
				</tr>
				<tr>
					<td><label class="bold">Email Address:</label></td>
					<td><input type="text" name="username" id="username" maxlength="120" size="22"></td>
					<td id="emailError" style="color: red"></td>
				</tr>
				
				<tr>
					<td><label class="bold">Password:</label></td>
					<td><input type="password" name="password" id="password" maxlength="8" size="22"></td>
					<td id="passwordError" style="color: red"></td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="2"><input type="button" class="button" value="Login" id="login" onclick="validateCredentials()"></td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="2"><input type="button" class="blackButton" value="New User? (Sign-Up)" id="signup" onclick="navigateToSignupPage()"></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2"><input type="button" class="blackButton" value="Forgot Password?" id="forgotPassword" onclick="navigateToForgotPasswordPage()"></td>
				</tr>
			</table>
		</form>
		<form id="registrationForm" action="/event_manager/register" method="post">
			<input type="hidden" id="toDo" name="toDo">
		</form>
		<form id="forgotPasswordForm" action="/event_manager/resetPassword" method="post">
			<input type="hidden" id="action" name="toDo" value="navigateToForgotPasswordPage">
		</form>
	</center>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>