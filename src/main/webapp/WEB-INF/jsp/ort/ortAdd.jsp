<%@ include file="../common/header.jsp"%>
<%@ include file="../common/navigation.jsp"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<div class="container">
	<div class="row">
		<div class="col-md-9 col-md-offset-3">



			<h1>Neuer Ort</h1>
			
			
			<c:choose>
			<c:when test="${param.stand == 'success'}">
			<div class="alert alert-success">Sie haben erfolgreich einen neuen Ort hinzufügt.</div>
    
			</c:when>
			</c:choose>
			<div>
    
			<form:form method="POST" modelAttribute="ortForm">
				<spring:bind path="ortsname">
					<div class="form-group">
						<form:input type="text" path="ortsname" class="form-control"
							placeholder="Ortsname" autofocus="true" style="width: 50%;"></form:input>
						<div style="color: red;"><form:errors path="ortsname" ></form:errors></div>
					</div>
				</spring:bind>

				<spring:bind path="stadt">
					<div class="form-group">
						<form:input type="text" path="stadt" class="form-control"
							placeholder="Stadt" autofocus="true" style="width: 50%;"></form:input>
						<div style="color: red;"><form:errors path="stadt" ></form:errors></div>
					</div>
				</spring:bind>
				<spring:bind path="adresse">
					<div class="form-group">
						<form:input type="text" path="adresse" class="form-control" style="width: 100%;"
							placeholder="Adresse" autofocus="true"></form:input>
						<div style="color: red;"><form:errors path="adresse"></form:errors></div>
					</div>
				</spring:bind>
				<spring:bind path="plz">
					<div class="form-group">
						<form:input type="text" path="plz" class="form-control"
							placeholder="PLZ" autofocus="true" style="width: 50%;"></form:input>

						<div style="color: red;">
							<form:errors path="plz"></form:errors>
						</div>

					</div>
				</spring:bind>
				<spring:bind path="land">
					<div class="form-group">
						<form:input type="text" path="land" class="form-control"
							placeholder="Land" autofocus="true" style="width: 50%;"></form:input>

						<div style="color: red;"><form:errors path="land"></form:errors></div>


					</div>
				</spring:bind>
				


				<div class="form-group">
					<button type="submit" class="btn btn-success">Speichern</button>

				</div>

			</form:form>
		</div>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>
