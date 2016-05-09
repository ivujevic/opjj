<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_14.model.BlogUser, java.util.HashMap, java.util.Map"%>

<% 
Object errorObj = request.getAttribute("errors");
Map<String, String> errors = errorObj == null ? new HashMap<String, String>() : 
	(HashMap<String, String>) errorObj;

String nickError = errors.get("nick");
nickError = nickError == null ? "" : nickError;

String passError = errors.get("password");
passError = passError == null ? "" : passError;

String firstNameError = errors.get("firstName");
firstNameError = firstNameError == null ? "" : firstNameError;

String lastNameError = errors.get("lastName");
lastNameError = lastNameError == null ? "" : lastNameError;

String emailError = errors.get("email");
emailError = emailError == null ? "" : emailError;

%>

<html>

<head>
<title>JBlog - Register</title>
</head>

<body>
	<jsp:include page="Header.jsp" />
	
	<div align="left">
		<form 
		action="<%=request.getContextPath()%>/servleti/register"
		method="post">
	
			<br>
			Nickname: <input
			type = "text"
			name = "nick"
			size = "30"> <font color="red"><%=nickError%></font>
		
			<br>
		
			Password: <input
			type = "password"
			name = "password"
			size = "30"> <font color="red"><%=passError%></font>
			
			<br>
			
			First name: <input
			type = "text"
			name = "firstName"
			size = "30"> <font color="red"><%=firstNameError%></font>
			
			<br>
		
			Last name: <input
			type = "text"
			name = "lastName"
			size = "30"> <font color="red"><%=lastNameError%></font>
		
			<br>
		
			E-Mail: <input
			type = "text"
			name = "email"
			size = "30"> <font color="red"><%=emailError%></font>
			
			<br>
			
			<input
			type = "submit"
			value = "Submit"
			>
			
		</form>
	</div>
</body>
</html>