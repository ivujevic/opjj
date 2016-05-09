<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List, hr.fer.zemris.java.tecaj_14.model.BlogUser, hr.fer.zemris.java.tecaj_14.dao.*"%>

<%
List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
String nick = (String)session.getAttribute("current.user.nick");
long id = (Long)session.getAttribute("current.user.id");
%>

<html>
<head>
	<title>
	JBlog - Index
	</title>
</head>

<body>
	<jsp:include page="Header.jsp" />
	<%if(nick == null) {%>
	
	<div align = "center">
		<h2>Log in:</h2><br>
		<form action = "<%=request.getContextPath()%>/servleti/main" method = "post">
			Username: <input name = "username" type = "text" size = "30"><br>
			Password: <input name = "password" type = "password" size = "30"><br>
			<input type = "submit" value = "Log in"><br>
		</form>
	</div>
	
	<br><br>
	
	<div align = "center">
		<a href="<%=request.getContextPath()%>/servleti/register">New to JBlog? Register now!</a>
	</div>
	
	<%}%>
	
	<br><br>
	
	<div align = "left">
		<h3>Our authors:</h3>
		<ul>
		<%for(BlogUser user : blogUsers) {
			out.write("<li><a href = \"" + request.getContextPath() + "/servleti/author/" + user.getNick() + 
					"\">" + user.getNick() + "</a></li>");
		}
		%>
		</ul>
	</div>
</body>
</html>