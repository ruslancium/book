<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="name.ruslan.model.Book"%>
<!DOCTYPE html >
<%
String imageURL=application.getInitParameter("imageURL"); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/bookstore.css" type="text/css" />
<script src="js/bookstore.js" type="text/javascript"></script>

</head>

<body>
	<tr>
		<td>Парам-тарам</td>		
	</tr>
	
	<c:forEach items="${books}" var="books">
		<tr>
			<td>${books.id} }</td>			
		</tr>
	</c:forEach>
	

</body>
</html>

