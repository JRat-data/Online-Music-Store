<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Invoice</title>
</head>
<body>
<h1>Invoice</h1>
<h2>Thanks for your order!</h2>

<ul>
	<li>Invoice number: ${invoice.invoiceId}</li>
	<li>Total price: ${invoice.totalAmount}</li>
	<!-- It would be nice to show lineitems, but we are not supporting them in InvoiceData -->
</ul>

<ul>	
	<li><a href="catalog.html">Browse Catalog </a> </li>
	<li><a href="cart.html">View Cart</a> </li>
	<li><a href="userWelcome.html">User Home </a> </li>
</ul>

</body>
</html>
