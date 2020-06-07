<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Music Store :: Add Successfull</title>
	</head>
	<body>
	<h1>Added ${productCode} CD to Cart Successfully</h1>
		
		<form action="<c:url value='/catalog.html'/>" method="get" id="float_left">
		  <input type="submit" value="Continue Shopping">
		</form> <br>
		<a href="cart.html">View Cart </a><br>
		<a href="userWelcome.html">Back to User Home </a>

	</body>
</html>