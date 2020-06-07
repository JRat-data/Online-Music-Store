<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--Based off cart.jsp murachMusicStore-->
<html>
	<head>
		<title>Music Store :: Cart</title>
	</head>
	<body>
	<h1>Your Cart </h1>

		<c:choose>
			<c:when test="${emptyCart != null}">
				<p>Your cart is empty.</p>
			</c:when>
			<c:otherwise>
				<table>
					<tr>
						<th align="center">Description</th>
						<th align="center" style="width:50px">Price</th>
						<th align="center">Qty</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach var="item" items="${cartData}">
						<tr class="cart_row">
							<td colspan="1" align="center">${item.description}</td>
							<td align="center">${item.price}</td>
							<td align="center">
								<form action="<c:url value='/update.html'/>">
								  <input type="hidden" name="productCode" 
										 value="<c:out value='${item.code}'/>">
								  <input type=text name="quantity" size="4"
										 value="<c:out value='${item.quantity}'/>" id="quantity">
								  <input type="submit" value="Update">
								</form>                  
							</td>
							<td>
								<form action="<c:url value='/remove.html'/>">
								  <input type="hidden" name="productCode"
										 value="<c:out value='${item.code}'/>">
								  <input type="submit" value="Remove">
								</form>        
							</td>
						</tr>
				  </c:forEach>
					<tr>
					  <td colspan="3">
						<p><b>To change the quantity for an item</b>, enter the new quantity 
							  and click on the Update button.</p>
						<p><b>To remove an item</b>, click on the Remove button.</p>
					  </td>
					  <td colspan="3">&nbsp;</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>

		
		<form action="<c:url value='/catalog.html'/>" method="get" id="float_left">
		  <input type="submit" value="Continue Shopping">
		</form> <br>
		
		<c:if test="${emptyCart == null}">
			<form action="<c:url value='/checkout.html'/>" method="post">
				<input type="submit" value="Checkout">
			</form> <br>
		</c:if>
		
		
		<a href="userWelcome.html">Back to User Home </a>

	</body>
</html>