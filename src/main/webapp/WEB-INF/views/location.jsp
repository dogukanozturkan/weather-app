<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Location</title>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
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
						<th>Location</th>
						<th width="100"></th>
						<th width="100"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${location}" var="location">
						<tr>
							<td>${location.locationName}</td>
							<td><a href="<c:url value='/edit-location-${location.id}' />" class="btn btn-success custom-width">edit</a></td>
							<td><a href="<c:url value='/delete-location-${location.id}' />" class="btn btn-danger custom-width">delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="well">
			<a href="<c:url value='/newlocation'/>">Add New Location</a>
		</div>
	</div>
</body>
</html>