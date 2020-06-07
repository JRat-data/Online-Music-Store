<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Menu</title>
</head>
<body>
	<h1>Admin Menu</h1>
	
	<form action="initDB.html" method="get">
		<input type="submit" value="Initialize DB"><br />
	</form>
	<form action="processInvoices.html" method="get">
		<input type="submit" value="Process Invoices"><br />
	</form>
	<form action="displayReports.html" method="get">
		<input type="submit" value="Display Reports"><br />
	</form>
		<form action="logout.html" method="get">
		<input type="submit" value="Logout"><br />
	</form>

</body>
</html>