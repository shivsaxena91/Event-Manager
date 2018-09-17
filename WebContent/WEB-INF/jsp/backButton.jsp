<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
function backToHomePage(){
	$("#homePageForm").submit();
}
</script>
</head>
<body>
	<form action="/event_manager/homepage" method="post" id="homePageForm">
		<input type="hidden" id="action" name="toDo" value="backToHomepage">
		<input type="button" id="backButton" class="backButton" value="Back To Homepage" onclick="backToHomePage()">
	</form>
</body>
</html>