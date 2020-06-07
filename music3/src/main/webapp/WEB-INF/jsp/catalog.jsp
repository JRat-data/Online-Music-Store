<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Music Store :: Catalog</title>
	</head>
	<body>
	<h1>List of Albums </h1>

		<p>
			<a href="../../sound/8601/sound.html">
				86 (the band) - <em>True Life Songs and Pictures</em> - (mp3 sample played using HTML5)
			</a><br>

			<a href="../../sound/pf01/sound.html">
				Paddlefoot - <em>The First CD</em> - (mp3 sample played using HTML5, with servlet URL for mp3 data)
			</a><br>

			<a href="../../sound/pf02/sound.html">
				Paddlefoot - <em>The Second CD</em> - (mp3 samples played using Murach's page)
			</a><br>

			<a href="../../sound/jr01/sound.html">
				Joe Rut - <em>Genuine Wood Grained Finish</em> - (mp3 samples played using Murach's page)
			</a>
		</p>
		
		<form action="<c:url value='/add.html'/>">
			<label>Choose a CD:</label>
			<select name="productCode">
			  <option value="8601">True Life Songs and Pictures</option>
			  <option value="pf01">The First CD</option>
			  <option value="pf02">The Second CD</option>
			  <option value="jr01">Genuine Wood Grained Finish</option>
			</select>
			<input type="submit" value="Add to Cart">
		</form>
		
		<a href="userWelcome.html">Back to User Home </a>

	</body>
</html>