<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Login Page</title>
</head>
<body>

	<c:if test="${error=='Invalid Credentials'}" var="error"
		scope="request">
		<I>Invalid Credentials. Enter Correct Credentials</I>
	</c:if>

	<form action="adminWelcome.html" method="post">
		Username:<input type="text" name="username" /> <br /> Password:<input
			type="password" name="password" /> <br /> <input type="submit"
			value="login" />
	</form>

</body>
</html>