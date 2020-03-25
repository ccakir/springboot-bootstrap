<%@ include file="../common/header.jsp"%>
<div class="container-fluid">

         

          

           

          <div class="row">

            <div class="col-lg-5">

             

              <!-- Basic Card Example -->
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary"><spring:message code="label.myaccount"/></h6>
                </div>
                <div class="card-body">
                  <table class="table ">
   
    <tbody>
      <tr>
        <td><spring:message code="label.firstname"/></td>
        <td>: ${user.firstName}</td>
      </tr>
      <tr>
        <td><spring:message code="label.lastname"/></td>
        <td>: ${user.lastName}</td>
      </tr>
      <tr>
        <td><spring:message code="label.email"/></td>
        <td>: ${user.email}</td>
      </tr>
      <tr>
        <td><spring:message code="label.password"/></td>
        <td>: <a href="${contextPath}/benutzer/myaccount/updatePassword" class="btn btn-info" role="button"><spring:message code="label.passwordchange"/></a></td>
      </tr>
      <tr>
        <td><spring:message code="label.jobLocation"/></td>
        <td>: ${user.ort.ortsname}</td>
      </tr>
       <tr>
        <td><spring:message code="label.role"/></td>
        <td>: ${user.roles[0].name}</td>
      </tr>
    </tbody>
  </table>
                 </div>
              </div>


           


            </div>

          </div>

        </div>
<%@ include file="../common/footer.jsp"%>