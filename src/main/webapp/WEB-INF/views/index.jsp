<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="panel panel-default">
			<!-- Default panel contents -->


		</div>
		<sec:authorize access="hasRole('ADMIN')">
			<div class="well">
				<a href="<c:url value='/location' />">Lokasyon Düzenle</a>
			</div>
		</sec:authorize>
		<div class="well">
			<a href="<c:url value='/weather' />">Hava Durumu</a>
		</div>
		<sec:authorize access="hasRole('ADMIN')">
			<div class="well">
				<a href="<c:url value='/list' />">Kullanıcı Düzenle</a>
			</div>
		</sec:authorize>
		<div class="well">
			<a href="<c:url value='/report' />">Raporlar</a>
		</div>
	</div>
</body>
</html>