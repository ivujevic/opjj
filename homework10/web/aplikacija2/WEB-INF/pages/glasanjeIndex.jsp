<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	
	
	<body>
		<h1>Glasanje za omiljeni band</h1>
		<p>Od sljedećih bandova,koji vam je najdraži? Kliknite na link kako biste glasali! 
		</p>
		
		<ol>
		
			<c:forEach items="${bands}" var="band">
					<li><a href="glasanje-glasaj?id=${band.key}">${band.value}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>