<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Colors</title>
</head>

<c:set var="color" value="${pickedBgCol}" />
<c:choose>
	<c:when test="${color == null}">
		<c:set var="color" value="white"></c:set>
	</c:when>
</c:choose>

<body bgcolor =${color}>
<a href="setcolor?pickedBgCol=WHITE">WHITE</a>
<br>
<a href="setcolor?pickedBgCol=RED">RED</a>
<br>
<a href="setcolor?pickedBgCol=GREEN">GREEN</a>
<br>
<a href="setcolor?pickedBgCol=CYAN">CYAN</a>
<br>
<br>
<br>
<br>
<a href= "index.jsp">Index
</body>
</html>