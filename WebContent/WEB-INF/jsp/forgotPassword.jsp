<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
		});
		
		function validateEmailAddress(){
			var errorFlag = false;
			var emailAddress = $("#emailAddress").val();
			$("#emailError").html("");
			
			if(emailAddress == ""){
				errorFlag = true;
				$("#emailError").html("Please, enter your email address.");
			}
			else if(!emailAddress.includes("@") || !emailAddress.includes(".")){
				errorFlag = true;
				$("#emailError").html("Please, enter a valid email address.");
			}
			
			if(!errorFlag){
				$("#forgotPasswordForm").submit();
			}
		}
</script>
<style>

</style>
<title>Forgot Password</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form action="/event_manager/homepage" method="post" id="homePageForm">
		<input type="submit" id="backButton" class="backButton" value="Back To Login">
</form>
	<center>
		<form id="forgotPasswordForm" action="/event_manager/resetPassword" method="post">
			<table>
				<tr>
					<td class="bold">Email Address: </td>
					<td><input type="text" id="emailAddress" name="emailAddress" size="22"></td>
					<td id="emailError" style="color: red"></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2"><input type="button" class="button" id="resetPasswordButton" value="Reset Password" onclick="validateEmailAddress()"></td>
				</tr>
			</table>		
			<input type="hidden" id="toDo" name="toDo" value="forgotPassword">
		</form>
	</center>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>