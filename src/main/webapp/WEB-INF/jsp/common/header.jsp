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

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="#">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">CoM.CaKiR</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="#">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Dashboard</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

     

      <!-- Nav Item - Kunden -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fa fa-industry"></i>
          <span>Kunden</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="">Alle Kunden</a>
            <a class="collapse-item" href="buttons.html">Neuer Kunde erstellen</a>
            <a class="collapse-item" href="cards.html">Kunde löschen</a>
          </div>
        </div>
      </li>

      <!-- Nav Item - Benutzer-->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilitiesw" aria-expanded="true" aria-controls="collapseUtilitiesw">
          <i class="fa fa-globe" ></i>
          <span>Ort</span>
        </a>
        <div id="collapseUtilitiesw" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="${pageContext.request.contextPath}/auth/admin/ort/alleorte">Alle Orte</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/auth/admin/ort/addort">Neuer Ort hinzufügen</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/auth/admin/ort/updateort">Ort ändern</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/auth/admin/ort/deleteort">Ort löschen</a>
            
          </div>
        </div>
      </li>
      
      <!-- Nav Item - Benutzer-->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fa fa-users"></i>
          <span><spring:message code="label.menu.user"/></span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="${pageContext.request.contextPath}/auth/admin/benutzer/allebenutzer/">Alle Benutzer</a>
            <a class="collapse-item" href="utilities-animation.html">Benutzer löschen</a>
            <a class="collapse-item" href="utilities-other.html">Other</a>
          </div>
        </div>
      </li>
      
      <!-- Nav Item - Kontakte-->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilitiese" aria-expanded="true" aria-controls="collapseUtilitiese">
          <i class="fa fa-envelope"></i>
          <span>Kontakte</span>
        </a>
        <div id="collapseUtilitiese" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="utilities-color.html">Alle Kontakte</a>
            <a class="collapse-item" href="utilities-border.html">Neuer Kontakt erstellen</a>
            <a class="collapse-item" href="utilities-animation.html">Kontakt löschen</a>
            </div>
        </div>
      </li>
      
      <!-- Nav Item - Tätigkeit-->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilitiesq" aria-expanded="true" aria-controls="collapseUtilitiesq">
          <i class="fa fa-microchip"></i>
          <span>Tätigkeiten</span>
        </a>
        <div id="collapseUtilitiesq" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="utilities-color.html">Alle Tätigkeiten</a>
            <a class="collapse-item" href="utilities-border.html">Neue Tätigkeit erstellen</a>
            <a class="collapse-item" href="utilities-animation.html">Tätigkeit löschen</a>
            
          </div>
        </div>
      </li>

      

      

      

     

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <!-- Topbar Search -->
          <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
            <div class="input-group">
              <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
              <div class="input-group-append">
                <button class="btn btn-primary" type="button">
                  <i class="fas fa-search fa-sm"></i>
                </button>
              </div>
            </div>
          </form>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

            <!-- Nav Item - Search Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                  <div class="input-group">
                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                      <button class="btn btn-primary" type="button">
                        <i class="fas fa-search fa-sm"></i>
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </li>
            

 			<li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="?lang=de">
                    <img alt=""  value="de" id="langImageDe" src="${pageContext.request.contextPath}/img/german.png" onMouseOver="this.style.cursor='pointer'">
              </a>
            </li>
            <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="?lang=en">
                    <img alt="" value="en" id="langImageEn" src="${pageContext.request.contextPath}/img/english.png" onMouseOver="this.style.cursor='pointer'">
              </a>
            </li>
            

            

            

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <c:if test="${pageContext.request.userPrincipal.name != null}">
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${pageContext.request.userPrincipal.name}</span>
                <i class="fa fa-address-card" aria-hidden="true"></i>

              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="${contextPath}/benutzer/myaccount/info">
                  <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                  <spring:message code="label.myaccount"/>
                </a>
                <a class="dropdown-item" href="${contextPath}/benutzer/myaccount/updatePassword">
                  <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                  <spring:message code="label.passwordchange"/>
                </a>
                <a class="dropdown-item" href="#">
                  <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                  Activity Log
                </a>
                <div class="dropdown-divider"></div>
                
        
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                 <spring:message code="label.logout"/>
                </a>
              </div>
            </li>
			</c:if>
          </ul>

        </nav>
        <!-- End of Topbar -->
        
        <script type="text/javascript">
   var langImageEn = document.getElementById("langImageEn");
   var langImageDe = document.getElementById("langImageDe");
   
   langImageEn.onclick = function() {
	   window.location.replace('?lang=' + langImageEn.getAttribute("value"));
	   }
   
   langImageDe.onclick = function() {
	   window.location.replace('?lang=' + langImageDe.getAttribute("value"));
	   }
</script>