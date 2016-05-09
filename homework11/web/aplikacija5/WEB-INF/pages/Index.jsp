<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ankete</title>
</head>
<body>
<h1>Ovdje su prikazane dostupne ankete!</h1> <br>
<c:set value="1" var="i"/>

 <c:forEach var = "id" items="${listId}">
 	<a href="glasanje?pollID=<c:out value="${id}" />">Anketa${i}</a><br>
 	<c:set value="${i+1}" var="i"/>
 </c:forEach>
</body>
</html>