<%@ include file="../common/header.jsp"%>
<%@ include file="../common/navigation.jsp"%>

<!-- Page Heading -->
          
          <p class="mb-4"></p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Alle Orte</h6>
               </div>
            <c:choose>
			<c:when test="${param.delete == 'success'}">
			<div class="alert alert-success">Datensatz wurde erfolgreich gelöscht.</div>
    		</c:when>
    		<c:when test="${param.delete == 'error'}">
			<div class="alert alert-danger">Ein Fehler ist aufgetreten.</div>
    		</c:when>
    		<c:when test="${param.delete == 'null'}">
			<div class="alert alert-warning">Sie haben keinen Datensatz ausgewählt!.</div>
    		</c:when>
    		</c:choose>
            <div class="card-body">
              <div class="table-responsive">
              <form:form method="POST" action="${contextPath}/ort/deleteort">
              <div style="padding-bottom: 10px">
             <button type="submit" class="btn btn-danger" >AUSGEWÄHLTE DATENSATZ LÖSCHEN</button>
             
              </div>
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                    <th></th>
                      <th>Ortsname</th>
                      <th>Stadt</th>
                      <th>Adresse</th>
                      <th>PLZ</th>
                      <th>Land</th>
                      <th>Löschen</th>
                      </tr>
                  </thead>
                  <tfoot>
                    <tr>
                    <th> </th>
                      <th>Ortsname</th>
                      <th>Stadt</th>
                      <th>Adresse</th>
                      <th>PLZ</th>
                      <th>Land</th>
                      <th>Löschen</th>
                    </tr>
                  </tfoot>
                  <tbody>
                  <c:forEach items="${ortDeleteList}" var="orte">
                   <tr>
                   	  <td align="right"><input type="checkbox" class="form-check-input" value="${orte.id}" name="ids"></td>
                   	  <td>${orte.ortsname}</td>
                      <td>${orte.stadt}</td>
                      <td>${orte.adresse}</td>
                      <td>${orte.plz}</td>
                      <td>${orte.land}</td>
                      <td><a href="${contextPath}/ort/deleteort/${orte.id}" class="btn btn-danger" role="button">LÖSCHEN</a></td>
                      
                    </tr>
                  </c:forEach>
                   
                   
                  </tbody>
                </table>
                </form:form>
              </div>
            </div>
          </div>

<%@ include file="../common/footer.jsp"%>
