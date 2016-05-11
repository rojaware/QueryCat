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
	<div class="generic-container">
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<img src="<c:url value="/static/images/cat.jpg" />" /><span
					class="lead">List of Query </span>
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
		<span class="well floatRight"> Go to <a
			href="<c:url value='/rest/list' />">Query List</a>
		</span>
	</div>
</body>
</html>