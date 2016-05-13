<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Request</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>

	<div class="generic-container">
		<div class="well lead">Query Parameters</div>
		<form:form method="POST" modelAttribute="query"
			class="form-horizontal">
			<form:input type="hidden" path="id" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="name">Name</label>
					<div class="col-md-7">
						<form:input type="text" path="name" id="name"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="name" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="sql">SQL</label>
					<div class="col-md-7">
						<form:textarea path="sql" rows="5" cols="30" id="sql"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="sql" class="help-inline" />
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>FIELD</th>
							<th>VALUE</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${query.map}" var="entry" varStatus="status">
							<tr>
								<td>"${entry.key}"</td>
								<td><input  name="map[${entry.key}]" value="${entry.value}"
							class="form-control input-sm" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="row">
				<div class="form-actions floatRight">
					<input type="submit" value="Execute" class="btn btn-primary btn-sm" />
					or <a href="<c:url value='/rest/list' />">Cancel</a>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>