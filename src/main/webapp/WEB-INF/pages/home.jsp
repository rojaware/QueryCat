<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>Spring MVC Form Handling</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
</head>
<body>
	<div class="container">

		<h1>
			<img src="<c:url value="/static/images/cat.jpg" />" />Query List
		</h1>
		<table class="table">
			<thead>
				<th>No</th>
				<th>Name</th>
				<th>SQL</th>
				<th><a href="#" class="btn btn-info btn-sm"> <span
						class="glyphicon glyphicon-plus"></span> New
				</a></th>

			</thead>
			<c:forEach var="query" items="${queryList}" varStatus="status">
				<tr class="success">
					<td>${query.id}</td>
					<td>${query.name}</td>
					<td>${query.sql}</td>
					<td><a href="#"><span class="glyphicon glyphicon-pencil"></span></a>
						<a href="#"> <span class="glyphicon glyphicon-trash"></span></a> 
						<a href="#"><span class="glyphicon glyphicon-play"></span></a>
					</td>


				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>