<%@ include file="../common/header.jsp"%>


<!-- Page Heading -->

<p class="mb-4"></p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">Alle Benutzer</h6>
	</div >
	<div class="card-body">
		<div class="table-responsive">
		<form class="form-inline">
		<div class="container-fluid">
		<div class="row">
		<div class="col-sm-2">
			<div class="form-group" class="form-inline">
				<label for="sel1" style="padding-right: 10px"><spring:message code="label.show"/></label>
				<select class="form-control form-control-sm" id="sel1" onchange="this.options[this.selectedIndex].value && (window.location = this.options[this.selectedIndex].value);">
					<option></option>
					<option value="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=5&pageNo=${pageNo}">5</option>
					<option value="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=10&pageNo=${pageNo}">10</option>
					<option value="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=20&pageNo=${pageNo}">20</option>
					<option value="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=50&pageNo=${pageNo}">50</option>
					<option value="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=100&pageNo=${pageNo}">100</option>
					<option value="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=200&pageNo=${pageNo}">200</option>
				</select><label for="sel1" style="padding-left: 10px"><spring:message code="label.entry"/></label> 
				</div>
			</div>
			
			
			</div>
			
			
			</div>
			
			
			</form>
			<table class="table table-light table-striped" id="dataTable"
				width="100%" cellspacing="0">
				<thead class="thead-dark">
					<tr>
						<th><spring:message code="label.firstname" /></th>
						<th><spring:message code="label.lastname" /></th>
						<th><spring:message code="label.email" /></th>
						<th><spring:message code="label.jobLocation" /></th>
						<th><spring:message code="label.role" /></th>
						<th><spring:message code="label.accountstatus" /></th>
						<th><spring:message code="label.accountverification" /></th>
					</tr>
				</thead>
				<tfoot class="thead-dark">
					<tr>
						<th><spring:message code="label.firstname" /></th>
						<th><spring:message code="label.lastname" /></th>
						<th><spring:message code="label.email" /></th>
						<th><spring:message code="label.jobLocation" /></th>
						<th><spring:message code="label.role" /></th>
						<th><spring:message code="label.accountstatus" /></th>
						<th><spring:message code="label.accountverification" /></th>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${benutzer}" var="benutzer">
						<tr>
							<td>${benutzer.firstName}</td>
							<td>${benutzer.lastName}</td>
							<td>${benutzer.email}</td>
							<td>${benutzer.ort.ortsname}</td>
							<td>${benutzer.roles[0].name}</td>
							<td><a href="#" class="${benutzer.enabled == 'true' ? 'btn btn-success btn-circle' : 'btn btn-danger btn-circle'}"></a></td>
							<td><c:if test="${benutzer.enabled == 'false'}"><a href="${contextPath}/admin/benutzer/userverification/${benutzer.id}" class="btn btn-info" role="button"><spring:message code="label.button.sendverificationemail"/></a></c:if></td>

						</tr>
					</c:forEach>


				</tbody>
			</table>

		</div>
		<div class="container">
			<ul class="pagination justify-content-center" style="margin: 20px 0">
				<% 
                String reqPageNo = request.getParameter("pageNo");
                
                int previous;
                int next;
                //Previous Rechner
                if(reqPageNo == null || Integer.parseInt(reqPageNo) == 1) previous = 0;
                else previous = Integer.parseInt(reqPageNo) - 1 ;
                
                //Next Rechner
                 if(reqPageNo == null || Integer.parseInt(reqPageNo) == 1) next = 2;
                else next = Integer.parseInt(reqPageNo) + 1;
                %>
				<li class="page-item ${pageNo == 1 ? 'disabled' : '' }"><a
					class="page-link"
					href="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=${pageSize}&pageNo=<%=previous%>"><spring:message
							code="label.button.previous" /></a></li>
				<c:forEach begin="1" end="${pages}" var="pageNumber">
					<li class="page-item ${pageNo == pageNumber ? 'active' : ''}"><a
						class="page-link"
						href="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=${pageSize}&pageNo=${pageNumber}">${pageNumber}</a></li>
				</c:forEach>

				<li class="page-item ${pageNo == pages ? 'disabled' : '' }"><a
					class="page-link"
					href="${pageContext.request.contextPath}/admin/benutzer/allebenutzer/?pageSize=${pageSize}&pageNo=<%=next%>"><spring:message
							code="label.button.next" /></a></li>
			</ul>
		</div>
	</div>

</div>

<%@ include file="../common/footer.jsp"%>
