<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List, hr.fer.zemris.java.tecaj_14.model.BlogUser, hr.fer.zemris.java.tecaj_14.dao.*, 
hr.fer.zemris.java.tecaj_14.dao.jpa.JPAEMProvider, hr.fer.zemris.java.tecaj_14.model.BlogEntry"%>
<%
	String nick = (String)session.getAttribute("current.user.nick");
	long id = (Long)session.getAttribute("current.user.id");
	BlogUser blogUser = null;
	
	String user = (String)request.getAttribute("user");

	blogUser = (BlogUser)JPAEMProvider.getEntityManager().
		createQuery("SELECT DISTINCT u FROM BlogUser AS u WHERE u.nick = :username").
		setParameter("username", user).getSingleResult();
	
	List<BlogEntry> userEntries = blogUser.getUserEntries();
%>

<html>
	<head>
	<title>JBlog - <%=user%>'s Blog Entries</title>
	</head>

	<body>
	<jsp:include page="Header.jsp" />
		
	<h1><%=user%>'s Blog Entries</h1>
	<br><br>
	<ul>
	<%for(BlogEntry entry : userEntries) {%>
	<li><a href="<%=request.getContextPath()%>/servleti/author/<%=user + "/" + entry.getId()%>">
	<%=entry.getTitle()%></a></li><br>
	<%}%>
	</ul>
	
	<%if(user.equals(nick)) {%>
	<a href="<%=request.getContextPath()%>/servleti/author/<%=user%>/new">Add New Entry</a>
	<%}%>
	
	</body>


</html>