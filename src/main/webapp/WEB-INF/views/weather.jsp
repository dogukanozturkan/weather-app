<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather</title>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<script>
	function myLocation() {
		var x = document.getElementById("mySelect").selectedIndex;
		var y = document.getElementById("mySelect").options;
		document.getElementById("queryLoc").href = "<c:url value='/weather-"+ y[x].index + "-" + y[x].text + "' />";
	}
</script>
</head>
<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">List of Locations </span>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Choose Location</th>
						<th width="100"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><select id="mySelect" onchange="myLocation(this);">
								<option value="0">Seçiniz...</option>
								<c:forEach items="${location}" var="location">
									<option value="${location.id}">${location.locationName}</option>
								</c:forEach>
						</select></td>
						<td><a href="" id="queryLoc" class="btn btn-success custom-width">Sorgula</a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="well">
			${message.getCurrentObservation().getDisplayLocation().getCity()} ${message.getCurrentObservation().getTempC()} <img src="${message.getCurrentObservation().getIconUrl()}">
			<c:if test="${empty message.getCurrentObservation() && not empty message.getResponse()}"> Sorgulama başarısız! </c:if>
		</div>
	</div>
</body>
</html>