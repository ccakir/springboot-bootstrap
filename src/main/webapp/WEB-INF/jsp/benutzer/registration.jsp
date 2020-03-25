<%@ include file="../common/header.jsp"%>
<%@ include file="../common/navigation.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">



			<h1>Registration</h1>
			<form:form method="POST" modelAttribute="userForm">

			<c:choose>
			<c:when test="${param.status == 'success'}">
			<div class="alert alert-success"><spring:message code="label.message.user_register_ok"/></div>
    		</c:when>
    		
    		</c:choose>
					


				<spring:bind path="firstName">
					<div class="form-group">
						<spring:message var="placeholderFirstname" code="label.firstname" />
						<form:input type="text" path="firstName" class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.firstName != null ? 'is-valid' : ''}"
							placeholder='${placeholderFirstname}' autofocus="true"></form:input>
						<div style="color: red;"><form:errors path="firstName"></form:errors></div>
					</div>
					
				</spring:bind>
				<spring:bind path="lastName">
					<div class="form-group">
						<spring:message var="placeholderLastname" code="label.lastname" />
						<form:input type="text" path="lastName" class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.lastName != null ? 'is-valid' : ''}"
							placeholder='${placeholderLastname}' autofocus="true"></form:input>
						<div style="color: red;"><form:errors path="lastName"></form:errors></div>
					</div>
				</spring:bind>
				<spring:bind path="email">
					<div class="form-group">
					<spring:message var="placeholderEmail" code="label.email" />
						<form:input type="text" path="email" class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.email != null ? 'is-valid' : ''}"
							placeholder='${placeholderEmail}' autofocus="true"></form:input>

						<div style="color: red;">
							<form:errors path="email" id="emailexisting"><spring:message code="label.error.emailexisting"/></form:errors>
							
							
						</div>
   						

					</div>
				</spring:bind>
				<spring:bind path="confirmEmail">
					<div class="form-group">
					<spring:message var="placeholderConfirmemail" code="label.confirmemail" />
						<form:input type="text" path="confirmEmail" class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.confirmEmail != null ? 'is-valid' : ''}"
							placeholder='${placeholderConfirmemail}' autofocus="true"></form:input>

						<div style="color: red;"><form:errors path="confirmEmail" id="emailconfirm"><spring:message code="label.error.emailconfirm"/></form:errors></div>


					</div>
					
				</spring:bind>
				<spring:bind path="password">
				<spring:message var="placeholderPassword" code="label.password" />
					<div class="form-group">
						<form:input id="password" type="password" path="password" class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.password != null ? 'is-valid' : ''}"
							placeholder='${placeholderPassword}'></form:input>
							<div style="color: red;">
						<c:forEach items="${status.errorMessages}" var="error">
        
       
	<c:forTokens var="token" items="${error}"
                     delims="-">
            <spring:message code="${token}"/></br>
        </c:forTokens>

 </c:forEach>
	</div>
	
    

					</div>
				</spring:bind>
				
				<spring:bind path="confirmPassword">
					<div class="form-group">
					<spring:message var="placeholderConfirmpassword" code="label.confirmpassword" />
						<form:input type="password" path="confirmPassword"
							class="form-control ${status.error ? 'is-invalid' :''} ${status.error == false && userForm.confirmPassword != null ? 'is-valid' : ''}" placeholder='${placeholderConfirmpassword}'></form:input>
						<div style="color: red;"><form:errors path="confirmPassword" id="password.confirm"><spring:message code="label.error.passwordconfirm"/></form:errors></div>
					</div>
				</spring:bind>
				
				<spring:bind path="ort">
					<div class="form-group">
						<label for="ort"><spring:message code="label.selectjoblocation"/></label> <select
							class="form-control" id="ort" name="ort">
							<c:forEach items="${ortList}" var="orte">
							<option value="${orte.id}" ${orte.id == selectedOrt ? 'selected="selected"' : ''}>${orte.ortsname}</option>
							</c:forEach>

						</select>
					</div>
				</spring:bind>

				<spring:bind path="roles">
					<div class="form-group">
						<label for="roles"><spring:message code="label.selectrole"/></label> <select
							class="form-control" id="roles" name="roles">

							<option value="ROLE_USER">USER</option>
							<option value="ROLE_ADMIN">ADMIN</option>

						</select>
					</div>
				</spring:bind>


				<div class="form-group">
					<button type="submit" class="btn btn-success btn-icon-split"> <span class="icon text-white-50">
                      <i class="fas fa-check"></i>
                    </span>
                    <span class="text">Register</span></button>
                    
                    
		<button type="reset" class="btn btn-danger btn-icon-split"> <span class="icon text-white-50">
                      <i class="fas fa-trash"></i>
                    </span>
                    <span class="text">Reset</span></button>
                    
                    
		
				</div>

			</form:form>
		</div>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>
