<%@page import="java.util.List"%>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Squares result</title>
	</head>
		
	<c:set var="color" value="${pickedBgCol}"/>
	<c:choose>
		<c:when test="${color == null}">
			<c:set var = "color" value ="white"></c:set>
		</c:when>
	</c:choose>
	
	<body bgcolor=${color} }>
			Rezultati su :
			<br>
			
			<c:forEach items="${results}" var="item">
				${item}<br>
			</c:forEach>
	</body>
</html>