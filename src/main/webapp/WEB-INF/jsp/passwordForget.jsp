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
 <!-- Begin Page Content -->
        <div class="container-fluid">

          <h1><spring:message code="label.passwordforget"/></h1>

		<c:choose>
				<c:when test="${status == 'success'}">
					<div class="alert alert-success">
						<spring:message code="message.passwordresetsuccess" />
					</div>
				</c:when>
				<c:when test="${status == 'UserNotFound'}">
					<div class="alert alert-danger"><spring:message code="message.passwordresetsenderror" /></div>
				</c:when>
				

			</c:choose>
		<h8><spring:message code="label.passwordforgettext1"/></h8>
		<br><br>
		<form:form method="post" action="${contextPath}/forgetpassword">
		<div class="input-group mb-3">
    <div class="input-group-prepend">
      <span class="input-group-text"><spring:message code="label.email"/></span>
    </div>
    
    <input type="text" name="email" class="form-control">
    
    
  </div>
  <button type="submit" class="btn btn-success"><spring:message code="label.button.send"/></button>
		</form:form>
        </div>
        <!-- /.container-fluid -->
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