<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	
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

		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Client Management Admin</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li class="active"><a
							href="http://localhost:8080/UserManagement/">Users</a></li>
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#">GoldTier<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a
									href="https://dtdwbd20:18001/ibm/console/login.do?action=secure">Websphere
										Admin</a></li>
								<li><a href="#">Application</a></li>
								<li><a href="#">Logs</a></li>
							</ul></li>
						<li><a class="dropdown-toggle" data-toggle="dropdown"
							href="#">PETR<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Application</a></li>
								<li><a href="#">Logs</a></li>
							</ul>
						<li><a href="#">Query Tool</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						
						<li><a href="<c:url value='/rest/logout' />"><span class="glyphicon glyphicon-log-out"></span>
								Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">List of Query on "${active}"</span> <img
					src="<c:url value="/static/images/cat.png" />" />
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>No</th>
						<th>Name</th>

						<th><sec:authorize access="hasRole('ADMIN')">
								<a href="<c:url value='/rest/newquery' />"
									class="btn btn-info btn-sm"> <span
									class="glyphicon glyphicon-plus"></span>
								</a>
								<a href="<c:url value='/rest/todo-list' />"
									class="btn btn-info btn-sm"> <span
									class="glyphicon glyphicon-cog"></span>
								</a>
							</sec:authorize></th>


					</tr>
				</thead>
				<tbody>
					<c:forEach items="${queryList}" var="query">
						<tr>
							<td>${query.id}</td>
							<td>${query.name}</td>
							<td><sec:authorize access="hasRole('ADMIN')">
									<a href="<c:url value='/rest/edit-query-${query.id}' />"> <span
										class="glyphicon glyphicon-pencil"></span>
									</a>
									<a href="<c:url value='/rest/delete-query-${query.id}' />">
										<span class="glyphicon glyphicon-trash"></span>
									</a>
								</sec:authorize> <a href="<c:url value='/rest/run-query-${query.id}' />"> <span
									class="glyphicon glyphicon-play"></span></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
</body>
</html>