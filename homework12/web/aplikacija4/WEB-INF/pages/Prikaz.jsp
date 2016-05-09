<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_14.model.BlogEntry,hr.fer.zemris.java.tecaj_14.model.BlogComment"%>
<%@page import="java.util.List"%>
<%
  BlogEntry blogEntry = (BlogEntry)request.getAttribute("blogEntry");
  String nick = (String)session.getAttribute("current.user.nick");
  String urlNick = (String)request.getAttribute("viewed.blog.user");
%>
<html>
  <body>
	<jsp:include page="Header.jsp" />
  <% if(blogEntry==null) { %>
    No such entry.

  <% } else { %>
    <h1><%= blogEntry.getTitle() %></h1> 
    <%if(nick.equals(urlNick)) {%> 
    <a href="<%=request.getContextPath()%>/servleti/author/<%=nick%>/edit?id=<%=blogEntry.getId()%>">
    Edit
    </a> <%}%>
    <p><%= blogEntry.getText() %></p>
    <% if(!blogEntry.getComments().isEmpty()) { %>
    <ul>
    <% for(BlogComment c : blogEntry.getComments()) { %>
    <li><div style="font-weight: bold">[Korisnik=<%= c.getUsersEMail() %>] <%= c.getPostedOn() %></div><div style="padding-left: 10px;"><%= c.getMessage() %></div></li>
    <% } %>  
    </ul>
    <% } %>
    <% if(nick != null) {%>
    	<form action ="<%=request.getContextPath()%>/servleti/addComment" method = "post">
    		Message<br>
		<textarea name="text" rows="5" cols="30">
		</textarea>
		<input type = "hidden" name = "id" value = "<%=blogEntry.getId() %>">
		<br><br>
    	<input type = "submit" value = "Leave Comment">
    	</form>
    <%} %>
    
  <% } %>

  </body>
</html>