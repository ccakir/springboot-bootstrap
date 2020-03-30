<%@ include file="../common/header.jsp"%>

	

<div class="container-fluid ">

 <div class="row">
  
  <div class="col"></div>
  
  


            <div class="col-xl-4 col-lg-7">

		<h1><spring:message code="label.button.sendverificationemail"/></h1>
		

			<c:choose>
				
				<c:when test="${status == 'success'}">
					<div class="alert alert-success"><spring:message code="message.emailsendsuccess" /></div>
				</c:when>
				<c:otherwise>
				

		<form:form action="${contextPath}/benutzer/userverification/sendVerificationEmail" method="post">
	 <table class="table ">
   <input type="hidden" value="${user.id}" name="userId"/>
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
      
    </tbody>
  </table>
<button type="submit" class="btn btn-success"><spring:message
								code="label.button.send" /></button>
			
</form:form>
</c:otherwise>
			</c:choose>
</div>

<div class="col"></div>
</div>

</div>
	<!-- End of Main Content -->

	<%@ include file="../common/footer.jsp"%>