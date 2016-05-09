<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List, hr.fer.zemris.java.tecaj_14.model.*, java.util.Date, java.util.ArrayList"%>

<%
BlogEntry entry = (BlogEntry)session.getAttribute("blog.entry");
String creator = (String)session.getAttribute("blog.user");
String title = entry == null || entry.getTitle() == null ? "" : entry.getTitle();
String text = entry == null || entry.getText() == null ? "" : entry.getText();
%>

<html>
	<head>
	<title>JBlog - Entry Creation/Editing</title>
	</head>
	
	<body>
	<jsp:include page="Header.jsp" />
	
	<div align="center">
		<form action="<%=request.getContextPath()%>/servleti/author/<%=creator%>/save" method="post">

			<b>Title</b><br>
			<input type = "text" name = "title" value = "<%=title%>" size = "30">
			
			<br>

			Message<br>
			<textarea name="text" rows="30" cols="80"> <%=text%></textarea>
			
			<br>
			
			<input type = "submit" value = "Submit">
		</form>
	</div>
	
	</body>
</html>