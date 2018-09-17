<%@page import="com.eventmanager.utilities.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String passwordResetStatus = (String)request.getSession().getAttribute(Constants.RESET_PASSWORD_STATUS);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
			$("#errorMessage").html("");
			<%if(passwordResetStatus!=null && !passwordResetStatus.equals("")){%>
			$("#errorMessage").html('<%=passwordResetStatus%>');
			<%}
			else{%>
				$("#errorMessage").html("");
			<%}%>
		});
		function validateCredentials(){
			var errorFlag = false;
			var currentPassword = $("#currentPassword").val();
			var newPassword = $("#newPassword").val();
			var confirmPassword = $("#confirmPassword").val();
			
			$("#currentPasswordError").html("");
			$("#newPasswordError").html("");
			$("#confirmPasswordError").html("");
			
			if(currentPassword == ""){
				errorFlag = true;
				$("#currentPasswordError").html("Please, enter your current password.");
			}
			else if(currentPassword.length != 8){
				errorFlag = true;
				$("#currentPasswordError").html("Invalid current password.");
			}
			
			if(newPassword == ""){
				errorFlag = true;
				$("#newPasswordError").html("Please, enter your new password.");
			}
			else if(newPassword.length != 8){
				errorFlag = true;
				$("#newPasswordError").html("Invalid new password.");
			}
			
			if(confirmPassword == ""){
				errorFlag = true;
				$("#confirmPasswordError").html("Please, confirm your password.");
			}
			else if(confirmPassword.length != 8){
				errorFlag = true;
				$("#confirmPasswordError").html("Invalid password.");
			}
			else if(newPassword != confirmPassword){
				errorFlag = true;
				$("#confirmPasswordError").html("Confirm password should be same as new password.");
			}
			
			if(currentPassword == newPassword){
				errorFlag = true;
				$("#newPasswordError").html("New password should not be same as current password.");
			}
			
			if(!errorFlag){
				$("#resetPasswordForm").submit();
			}
		}
	</script>
<title>Reset Password</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<form action="/event_manager/logout" method="post" id="logoutForm">
		<input type="button" class="logoutButton" id="logout" value="Logout" onclick="logoutFromEventManager()">
	</form>
	<jsp:include page="backButton.jsp"/>
	<center>
	<h1>Reset Password</h1>
		<form id="resetPasswordForm" action="/event_manager/resetPassword" method="post">
			<table>
				<tr>
					<td colspan="3" id="errorMessage" style="color: red"></td>
				</tr>
				<tr>
					<td class="bold">Current Password: </td>
					<td><input type="password" id="currentPassword" name="currentPassword" maxlength="8"></td>
					<td id="currentPasswordError" style="color: red"></td>
				</tr>
				<tr>
					<td class="bold">New Password: </td>
					<td><input type="password" id="newPassword" name="newPassword" maxlength="8"></td>
					<td id="newPasswordError" style="color: red"></td>
				</tr>
				<tr>
					<td class="bold">Confirm Password: </td>
					<td><input type="password" id="confirmPassword" name="confirmPassword" maxlength="8"></td>
					<td id="confirmPasswordError" style="color: red"></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2"><input class="button" type="button" value="Reset Password" onclick="validateCredentials()"> </td>
				</tr>
			</table>		
			<input type="hidden" id="toDo" name="toDo" value="resetPassword">
		</form>
	</center>
<jsp:include page="footer.jsp"></jsp:include>	
</body>
</html>