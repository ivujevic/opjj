<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title> Main page</title>
	</head>
	<body 
			<%
				String tempColor =(String) session.getAttribute("pickedBgCol");
				String color ="white";
				if(tempColor != null) {
					color = tempColor;
				}
			%>
	bgcolor= <%=color%>>
		<a href="colors.jsp">Choose color</a>
		<br>
		<a href="squares?a=100&b=120">Calculate square of numbers [100,120]</a>
		<br>
		<a href="stories/funny.jsp">Funny stories</a>
		<br>
		<a href="powers?a=1&b=100&n=3">Powers</a>
		<br>
		<a href="appinfo.jsp">Info</a>
		<br>
		<a href="glasanje">Glasanje</a>
	</body>
</html>