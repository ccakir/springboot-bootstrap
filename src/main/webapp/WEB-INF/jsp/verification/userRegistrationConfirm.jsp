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

	<div class="container" style="width: 1000px; padding-top: 50px">





	<div class="row">

		<div class="col"></div>




		<div class="col-xl-10 col-lg-7">

			<h1>
				<spring:message code="message.verificationtokentitel" />
			</h1>


			<c:choose>

				<c:when test="${token == 'invalidToken'}">
					<div class="alert alert-danger">
						<spring:message code="message.verificationtokeninvalid" />
					</div>
				</c:when>
				<c:when test="${token == 'expired'}">
					<div class="alert alert-info">
						<spring:message code="message.verificationexpired" />
					</div>
				</c:when>
				<c:when test="${token == 'valid'}">
				<form:form method="post" action="login">
				<input type="hidden" name="username" value="${username}"/>
				<input type="hidden" name="password" value="${password}"/>
					<div class="alert alert-success">
						<spring:message code="message.verificationtokenvalid" />
					</div><br>
					<button type="submit" class="btn btn-success">Login</button>
					</form:form>
				</c:when>
				
			</c:choose>
		</div>

		<div class="col"></div>
	</div>

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


