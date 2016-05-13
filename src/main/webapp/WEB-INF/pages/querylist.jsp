<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<span
					class="lead">List of Query </span>
					<img src="<c:url value="/static/images/cat.png" />" />
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>No</th>
						<th>Name</th>
						
						<th><a href="<c:url value='/rest/newquery' />"
							class="btn btn-info btn-sm"> <span
								class="glyphicon glyphicon-plus"></span> 
						</a>
						<a href="<c:url value='/rest/newquery' />"
							class="btn btn-info btn-sm"> <span
								class="glyphicon glyphicon-cog"></span> 
						</a></th>
						
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${queryList}" var="query">
						<tr>
							<td>${query.id}</td>
							<td>${query.name}</td>
							
							<td><a href="<c:url value='/rest/edit-query-${query.id}' />" >
								<span class="glyphicon glyphicon-pencil"></span></a> 
							<a href="<c:url value='/rest/delete-query-${query.id}' />" > 
								<span class="glyphicon glyphicon-trash"></span></a> 
							<a href="<c:url value='/rest/run-query-${query.id}' />" >
								<span class="glyphicon glyphicon-play"></span></a></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
</body>
</html>