<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>

<c:set var="color" value="${pickedBgCol}" />
<c:choose>
	<c:when test="${color == null}">
		<c:set var="color" value="white"></c:set>
	</c:when>
</c:choose>
</head>
<body  bgcolor =${color}>


	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati</p>
	
		<!-- napravi tablicu rezultata -->
		<table border="1" cellspacing = "0" class ="rez">
			<thead> <tr> <th>Bend</th><th>Broj glasova</th></tr></thead>
			<tbody>
				<c:set var = "max" value="${0}"/>
				
				<c:forEach var = "rezultat" items = "${voteResults}">
						<tr><td><c:out value="${bands[rezultat.key]}"/></td><td><c:out value="${rezultat.value}"/></td></tr> 
						<c:if test="${rezultat.value > max}">
								<c:set var="max" value="${rezultat.value}" />
						</c:if>
						
				</c:forEach>
			</tbody>
			
		</table>
		
		
		<img alt = "Pie-chart" src ="glasanje-grafika" width="400" height="400" />
		<h2>Grafički prikaz rezultata</h2>
		<p> Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
		
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bandova:</p>
	<ul>
		<c:forEach var = "rezultat" items = "${voteResults}">
					<c:if test="${rezultat.value == max}">
						<li><a href="${songs[rezultat.key]}" target="_blank"> <c:out value="${bands[rezultat.key]}"></c:out>
					</c:if>	
		</c:forEach>
	</ul>
</body>
</html>