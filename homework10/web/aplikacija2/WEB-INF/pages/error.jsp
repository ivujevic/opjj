<%@page import="java.util.Random"%>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Error!</title>
	</head>
	
		<%
				String tempColor =(String) session.getAttribute("pickedBgCol");
				String color ="white";
				if(tempColor != null) {
					color = tempColor;
				}
			%>
	
	<body bgcolor= <%=color%>>
		<font color="red" size="14">ERROR!</font>
	</body>
</html>