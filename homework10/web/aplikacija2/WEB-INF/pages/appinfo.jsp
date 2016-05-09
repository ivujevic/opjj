<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Info</title>
	</head>
<body>
	<%
		long loadTime = Long.valueOf(request.getServletContext().getAttribute("loadTime").toString());
		long currentTime = System.currentTimeMillis();
		long distance = currentTime-loadTime;
		
		StringBuilder builder = new StringBuilder();
		
		long days = distance/(24*60*60*1000);
		long hours = (distance % (24*60*60*1000))/(60*60*1000);
		long minutes = (((distance%  (24*60*60*1000)))%(60*60*1000))/(60*1000);
		long seconds = ((((distance%  (24*60*60*1000)))%(60*60*1000))%(60*1000))/1000;
		long miliseconds = (((((distance%  (24*60*60*1000)))%(60*60*1000))%(60*1000)))%1000;
		if(days >0) {
			builder.append(days+" days ");
		}
		if(hours>0) {
			builder.append(hours + " hours ");
		}
		if(minutes>0) {
			builder.append(minutes + " minutes ");
		}
		
		if(seconds>0) {
			builder.append(seconds + " seconds ");
		}
		
		if(miliseconds>0) {
			builder.append(miliseconds + " miliseconds ");
		}
		
	%>
	
	<%=builder.toString() %>
</body>
</html>