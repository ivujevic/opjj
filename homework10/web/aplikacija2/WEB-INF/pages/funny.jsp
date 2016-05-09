<%@page import="java.util.Random"%>
<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<%
		Random seed = new Random();
		int R = seed.nextInt(255);
		int G = seed.nextInt(255);
		int B = seed.nextInt(255);
		String tempColor =(String) session.getAttribute("pickedBgCol");
		String color ="white";
		if(tempColor != null) {
			color = tempColor;
		}
	%>

<head>
<title>Fuuny storie</title>
</head>

<body bgcolor= <%=color%>>

	<font color="rgb(<%=R%>,<%=G%>,<%=B%>)">Hrvatska je pobijedila Škotsku :)</font>
</body>
</html>