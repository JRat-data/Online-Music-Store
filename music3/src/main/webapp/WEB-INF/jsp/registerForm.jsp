<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Register</title>
</head>
<body>
<h1>Register</h1>
<h2>Create a user account or sign in</h2>

		<form method="get" action="register.html">
			First name: <input type="text" name="firstName"> <br />
			Last name: <input type="text" name="lastName"> <br />
			Email Address: <input type="text" name="email"> <br />
			<input type="submit" value="Register">
		</form>

<ul>	
	<li><a href="catalog.html">Browse Catalog </a> </li>
	<li><a href="cart.html">View Cart </a> </li>
	<li><a href="userWelcome.html">User Home </a> </li>
</ul>

</body>
</html>
