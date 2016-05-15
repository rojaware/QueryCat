<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration Confirmation Page</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<style>
.error {
	color: #ff0000;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="panel-heading"></div>
		<div class="well">
			<h1>Target Database</h1>
			<p>You can switch target database for query</p>

			<form:form modelAttribute="envBean" method="POST" action="./env">
				<ul class="list-group">
					<form:radiobuttons path="env" items="${databaseList}" element="li" />
				</ul>
				<form:errors path="env" cssClass="error" />
				<p>



					<input type="submit" value="Submit" class="btn btn-primary">
			</form:form>

		</div>
		<span class="glyphicon glyphicon-plus"></span> <span class="lead">To
			do list</span>
		<div class="well">
			<c:if test="${not empty todoList}">
				<ul class="list-group">
					<c:forEach var="listValue" items="${todoList}">
						<li class="list-group-item"><span
							class="glyphicon glyphicon-plus"></span>${listValue}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>

		<span class="pull-right"> <a
			href="<c:url value='/rest/list' />" class="btn btn-info btn-lg">
				<span class="glyphicon glyphicon-home"></span>
		</a>
		</span>
	</div>
</body>

</html>