<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather Reports</title>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<script src="<c:url value="/static/js/jquery-3.2.1.min.js"/>"></script>
<script>
	function queryStatus() {
		var queryStatusx = document.getElementById("queryStatus").selectedIndex;
		var queryStatusy = document.getElementById("queryStatus").options;
		
		var locationIdx = document.getElementById("myLocationIds").selectedIndex;
		var locationIdy = document.getElementById("myLocationIds").options;
		
		document.getElementById("filter").href = "<c:url value='/report&" + locationIdy[locationIdx].text + "&" + queryStatusy[queryStatusx].text + "' />";
		if($('#fromDate').val()) console.log($('#fromDate').val());
	}
	
	$(document).ready( function() {
	    var now = new Date();
	 
	    var day = ("0" + now.getDate()).slice(-2);
	    var month = ("0" + (now.getMonth() + 1)).slice(-2);

	    var today = now.getFullYear() + "-" + (month) + "-" + (day);


	   //$('#fromDate').val(today);
	    
	    $('#datebtn').click(function(){
	        
	        getDates();
	        
	    });
	});
	function getDates()
	{
	  if($('#fromDate').val() && $('#toDate').val()) 
	      document.getElementById("filter").href = $('#filter').attr("href") + "&" + $('#fromDate').val() + "&" + $('#toDate').val();
	  else 
		  document.getElementById("filter").href = $('#filter').attr("href") + "&0&0";
	}


</script>
</head>
<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">RAPORLAR</span>
				
                <a href="<c:url value='/report&HEPSI&HEPSI' />" id="filter" style="float: right;" class="btn btn-success custom-width" onclick="getDates();">Filtrele</a><br> 
				<b>Başlangıç Tarihi:</b> <input type="date" id="fromDate" name="fromDate"> 
				<b>Bitiş Tarihi:</b> <input type="date" id="toDate" name="toDate"> 
				<b>Lokasyon ID:</b> <select id="myLocationIds" onchange="queryStatus(this);">
											<option value="0">HEPSI</option>
										<c:forEach items="${allLocationId}" var="allLocationId">
											<option value="${allLocationId}">${allLocationId}</option>
										</c:forEach>
				                    </select>
				<b>Sorgulama Durumu:</b> <select id="queryStatus" onchange="queryStatus(this);">
								  		  <option value="0">HEPSI</option>
								  		  <option value="1">BASARILI</option>
								  		  <option value="2">BASARISIZ</option>
								  		  
				</select>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Kullanıcı ID</th>
						<th>Sorgulama Zamanı</th>
						<th>Lokasyon ID</th>
						<th>IP Adresi</th>
						<th>Sorgulama Sonucu</th>
						<th>Sonuç Getirme Süresi</th>
						<th>Sorgulama Durumu</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${report}" var="report">
						<tr>
							<td>${report.reportUserId}</td>
							<td>${report.reportTime}</td>
							<td>${report.reportLocationId}</td>
							<td>${report.reportUserIpAddress}</td>
							<td>${report.reportResult}</td>
							<td>${report.reportPassingTime}</td>
							<td>${report.reportStatus}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>