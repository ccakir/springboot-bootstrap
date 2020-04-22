<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page session="false"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Dashboard</title>

  <!-- Custom fonts for this template-->
  <link href="${contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="${contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
  <link href="${contextPath}/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

	

<div class="container-fluid" style="padding-top: 100px">


 <div class="row">
  
  <div class="col"></div>
  
  


            <div class="col-xl-4 col-lg-7">
<div style="text-align: right;">
	<a  href="${contextPath}/welcome/registration?lang=de">
                    <img alt="" src="${pageContext.request.contextPath}/img/german.png" >
              </a>
              
    <a  href="${contextPath}/welcome/registration?lang=en">
                    <img alt=""  src="${pageContext.request.contextPath}/img/english.png" >
              </a>
	</div>
		<h1>Registration</h1>
		<form:form method="POST" modelAttribute="userForm"
			action="">

			<c:choose>
				<c:when test="${param.register == 'success'}">
					<div class="alert alert-success">
						<spring:message code="message.registration.success" />
					</div>
				</c:when>
				<c:when test="${status == 'error'}">
					<div class="alert alert-danger"><spring:message code="message.registration.error" /></div>
				</c:when>
				

			</c:choose>



			<spring:bind path="firstName">
				<div class="form-group">
					<spring:message var="placeholderFirstname" code="label.firstname" />
					<form:input type="text" path="firstName"
						class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.firstName != null ? 'is-valid' : ''}"
						placeholder='${placeholderFirstname}' autofocus="true"></form:input>
					<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:forTokens var="token" items="${error}" delims="-">
								<spring:message code="${token}" />
								</br>
							</c:forTokens>
						</c:forEach>
					</div>
				</div>

			</spring:bind>
			<spring:bind path="lastName">
				<div class="form-group">
					<spring:message var="placeholderLastname" code="label.lastname" />
					<form:input type="text" path="lastName"
						class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.lastName != null ? 'is-valid' : ''}"
						placeholder='${placeholderLastname}' autofocus="true"></form:input>
					<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:forTokens var="token" items="${error}" delims="-">
								<spring:message code="${token}" />
								</br>
							</c:forTokens>
						</c:forEach>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="email">
				<div class="form-group">
					<spring:message var="placeholderEmail" code="label.email" />
					<form:input type="text" path="email"
						class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.email != null ? 'is-valid' : ''}"
						placeholder='${placeholderEmail}' autofocus="true"></form:input>
						<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:forTokens var="token" items="${error}" delims="-">
								<spring:message code="${token}" />
								</br>
							</c:forTokens>
						</c:forEach>
					</div>


				</div>
			</spring:bind>
			<spring:bind path="confirmEmail">
				<div class="form-group">
					<spring:message var="placeholderConfirmemail"
						code="label.confirmemail" />
					<form:input type="text" path="confirmEmail"
						class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.confirmEmail != null ? 'is-valid' : ''}"
						placeholder='${placeholderConfirmemail}' autofocus="true"></form:input>

					<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:forTokens var="token" items="${error}" delims="-">
								<spring:message code="${token}" />
								</br>
							</c:forTokens>
						</c:forEach>
					</div>


				</div>

			</spring:bind>
			<spring:bind path="password">
				<spring:message var="placeholderPassword" code="label.password" />
				<div class="form-group">
					<form:input id="password" type="password" value="123456Ww%"
						path="password"
						class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.password != null ? 'is-valid' : ''}"
						placeholder='${placeholderPassword}'></form:input>
					<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:forTokens var="token" items="${error}" delims="-">
								<spring:message code="${token}" />
								</br>
							</c:forTokens>
						</c:forEach>
					</div>



				</div>
			</spring:bind>

			<spring:bind path="confirmPassword">
				<div class="form-group">
					<spring:message var="placeholderConfirmpassword"
						code="label.confirmpassword" />
					<form:input type="password" path="confirmPassword"
						value="123456Ww%"
						class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.confirmPassword != null ? 'is-valid' : ''}"
						placeholder='${placeholderConfirmpassword}'></form:input>
					<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">


							<c:forTokens var="token" items="${error}" delims="-">
								<spring:message code="${token}" />
								</br>
							</c:forTokens>

						</c:forEach>
					</div>
				</div>
			</spring:bind>

			<spring:bind path="ort">
				<div class="form-group">
					<label for="ort"><spring:message
							code="label.selectjoblocation" /></label> <select class="form-control"
						id="ort" name="ort">
						<c:forEach items="${ortList}" var="orte">
							<option value="${orte.id}"
								${orte.id == selectedOrt ? 'selected="selected"' : ''}>${orte.ortsname}</option>
						</c:forEach>

					</select>
				</div>
			</spring:bind>




			<div class="form-group">
				<button type="submit" class="btn btn-success btn-icon-split">
					<span class="icon text-white-50"> <i class="fas fa-check"></i>
					</span> <span class="text">Register</span>
				</button>


				<button type="reset" class="btn btn-danger btn-icon-split">
					<span class="icon text-white-50"> <i class="fas fa-trash"></i>
					</span> <span class="text">Reset</span>
				</button>
				
				<a href="${contextPath}/login" class="btn btn-primary" role="button">Login</a>



			</div>

		</form:form>
</div>

<div class="col"></div>
</div>

</div>
	<!-- End of Main Content -->
 
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; CoM.CaKiR 2020</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    
    <!-- End of Content Wrapper -->

  
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel"><spring:message code="label.logout.title"/></h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">Ã</span>
          </button>
        </div>
        <div class="modal-body"><spring:message code="label.logout.question"/></div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal"><spring:message code="label.cancel"/></button>
         <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <button class="btn btn-primary" ><spring:message code="label.logout"/></button>
             </form>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="${contextPath}/vendor/jquery/jquery.min.js"></script>
  <script src="${contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="${contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="${contextPath}/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="${contextPath}/vendor/chart.js/Chart.min.js"></script>

  <!-- Page level plugins -->
  <script src="${contextPath}/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="${contextPath}/vendor/datatables/dataTables.bootstrap4.min.js"></script>
  <!-- Page level custom scripts -->
  <script src="${contextPath}/js/demo/chart-area-demo.js"></script>
  <script src="${contextPath}/js/demo/chart-pie-demo.js"></script>
   <!-- Page level custom scripts -->
  <script src="${contextPath}/js/demo/datatables-demo.js"></script>

</body>

</html>