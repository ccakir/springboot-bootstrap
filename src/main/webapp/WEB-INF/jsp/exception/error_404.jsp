<%@ include file="../common/header.jsp"%>
<!-- Page Heading -->
          

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary"><spring:message code="label.errorpage"/></h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <c:choose>
                <c:when test="${exception == 'UserNotFoundException'}">
					<div class="alert alert-danger"><spring:message code="message.usernotfound" /></div>
				</c:when>
				
				</c:choose>
              </div>
            </div>
          </div>
          
          <%@ include file="../common/footer.jsp"%>