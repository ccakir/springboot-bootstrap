<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Custom fonts for this template-->
<link href="${contextPath}/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="${contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
<link
	href="${contextPath}/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">

</head>
<body>

	<div class="container" style="width: 500px; padding-top: 50px">
	<div style="text-align: right;">
	<a  href="${contextPath}/login?lang=de">
                    <img alt="" src="${pageContext.request.contextPath}/img/german.png" >
              </a>
              
    <a  href="${contextPath}/login?lang=en">
                    <img alt=""  src="${pageContext.request.contextPath}/img/english.png" >
              </a>
	</div>
	
		<c:choose>
			<c:when test="${exception == 'UserAlreadyExists'}">
				<div class="alert alert-danger">
					<strong><spring:message code="message.attention"/>! </strong><spring:message code="message.useralreadyexist"/>
				</div>
			</c:when>
			<c:when test="${param.register == 'success'}">
				<div class="alert alert-danger">
					<strong><spring:message code="message.attention"/>! </strong><spring:message code="message.useralreadyexist"/>
				</div>
			</c:when>
			<c:when test="${param.message}">
				<div class="alert alert-danger">
					<strong><spring:message code="message.attention"/>! </strong>${param.message}
				</div>
			</c:when>
			<c:when test="${param.passwordReset == 'success'}">
				<div class="alert alert-success">
					<spring:message code="message.passwordresetsucces"/>
				</div>
			</c:when>
		</c:choose>
		<form action="login" class="was-validated" method="post">
			<div class="form-group ${error != null ? 'has-error' : ''}">
				<label for="username">Email:</label> <input type="text"
					class="form-control" id="username" placeholder="Email"
					name="username" required>

			</div>
			<div class="form-group ${error != null ? 'has-error' : ''}">
				<label for="password">Password:</label> <input type="password"
					class="form-control" id="password" placeholder="Password"
					name="password" required> <span>${error}</span> <input
					type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</div>

			<button type="submit" class="btn btn-success">Login</button>
			<c:choose>
			<c:when test="${lang == null}">
			<c:set var="lang" value="de"/>
			</c:when>
			<c:otherwise>
			<c:set var="lang" value="${lang}"/>
			</c:otherwise>
			</c:choose>
			<a role="button" class="btn btn-link" href="${contextPath}/welcome/forgetpassword?lang=${lang}"><spring:message code="label.button.passwordforget"/></a>
			<a role="button" class="btn btn-link" href="${contextPath}/welcome/registration?lang=${lang}"><spring:message code="label.registerhere"/></a>

		</form>
	</div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>

<!-- Bootstrap core JavaScript-->
	<script src="${contextPath}/vendor/jquery/jquery.min.js"></script>
	<script
		src="${contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="${contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="${contextPath}/js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script src="${contextPath}/vendor/chart.js/Chart.min.js"></script>

	<!-- Page level plugins -->
	<script src="${contextPath}/vendor/datatables/jquery.dataTables.min.js"></script>
	<script
		src="${contextPath}/vendor/datatables/dataTables.bootstrap4.min.js"></script>
	<!-- Page level custom scripts -->
	<script src="${contextPath}/js/demo/chart-area-demo.js"></script>
	<script src="${contextPath}/js/demo/chart-pie-demo.js"></script>
	<!-- Page level custom scripts -->
	<script src="${contextPath}/js/demo/datatables-demo.js"></script>

</body>

</html>

