<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
Long id = (Long)session.getAttribute("current.user.id");
String firstName = (String)session.getAttribute("current.user.fn");
String lastName = (String)session.getAttribute("current.user.ln");
%>

<html>
<body>
	<%if(id != null) {%>

	<div align = "center">

		<h5>Welcome, <%=firstName + " " + lastName%>! --
		<a href="<%=request.getContextPath()%>/servleti/logout">Log Out</a>
		</h5>
	</div>
	
<% } else { %>
	<div align = "center">

		<h5>
		You are not logged in
		</h5>
	</div>
<%}%>
	<div align = "center" style="font-weight: bold">
	<br>
	<a href="<%=request.getContextPath() %>/index.jsp">Return to Index</a>
	</div>
</body>
</html>