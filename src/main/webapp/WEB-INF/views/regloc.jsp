<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Location Registration Form</title>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="well lead">Location Registration Form</div>
		<form:form method="POST" modelAttribute="location" class="form-horizontal">
			<form:input type="hidden" path="id" id="id" />
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="locationName">Location Name</label>
					<div class="col-md-7">
						<form:input type="text" path="locationName" id="locationName" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="locationName" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" class="btn btn-primary btn-sm" /> or <a href="<c:url value='/location' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register" class="btn btn-primary btn-sm" /> or <a href="<c:url value='/location' />">Cancel</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>