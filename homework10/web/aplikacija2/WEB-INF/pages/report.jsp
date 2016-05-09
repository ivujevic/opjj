<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="org.jfree.chart.ChartUtilities"%>
<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

	<head>
		<title>Reports</title>
	</head>
	
		<%
				String tempColor =(String) session.getAttribute("pickedBgCol");
				String color ="white";
				if(tempColor != null) {
					color = tempColor;
				}
			%>
	
	<body bgcolor= <%=color%>>
		<h1> OS usage</h1>
		<p> Here are the results of OS usage in survey that we completed.</p>
			<%
				JFreeChart chart =(JFreeChart) request.getAttribute("reportImage");
				response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 500, 500, true, 0);
			%>
	</body>
</html>