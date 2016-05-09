<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	
	
	<body>
		<h1>${Title}</h1>
		<p>${Message}
		</p>
		
		<ol>
		
			<c:forEach items="${PollOptions}" var="option">
					<li><a href="glasanje-glasaj?PollID=${option.key}">${option.value}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>