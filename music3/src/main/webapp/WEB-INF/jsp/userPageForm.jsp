<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: User Page</title>
</head>
<body>
	<h1>Register Customer and Customer Address</h1>
	<h2>Create a user customer account with contact
		information${fName} ${lName} ${emailAddr}</h2>

	<form method="get" action="userPage.html">
		First name: <input type="text" name="firstName" value="${firstName}"><br> 
		Last name: <input type="text" name="lastName" value="${lastName}"> <br> 
		Email Address: <input type="text" name="email" value="${emailAddr}"> <br>
		StreetAddress: <input type="text" name="address"> <br> 
		<input type="submit" value="Register">
	</form>

	<ul>
		<li><a href="catalog.html">Browse Catalog </a></li>
		<li><a href="cart.html">View Cart </a></li>
		<li><a href="userWelcome.html">User Home </a></li>
	</ul>
</body>
</html>
