<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query List</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="w3-container w3-green">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
			
				<a href="<c:url value='/rest/downloadCSV' />"
					class="btn btn-info btn-lg"> <span
					class="glyphicon glyphicon-download-alt"></span> CSV</a>
				<span class="lead"> ${query.name} </span> 
			    <span class="pull-right">
				    <a href="<c:url value='/rest/list' />" class="btn btn-info btn-lg">
					<span class="glyphicon glyphicon-home"></span></a>
				</span>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<c:forEach items="${tableView.columns}" var="column">
							<th>${column}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tableView.rows}" var="row">
						<tr>
							<c:forEach items="${row}" var="value">
								<td>${value}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<span class="pull-right">
		    <a href="<c:url value='/rest/list' />" class="btn btn-info btn-lg">
			<span class="glyphicon glyphicon-home"></span></a>
		</span>
	</div>
	<img src="<c:url value="/static/images/cat.jpg" />" />
</body>
</html>