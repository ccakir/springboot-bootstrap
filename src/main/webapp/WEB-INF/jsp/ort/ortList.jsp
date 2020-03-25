<%@ include file="../common/header.jsp"%>
<%@ include file="../common/navigation.jsp"%>

<!-- Page Heading -->
          
          <p class="mb-4"></p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Alle Orte</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th width="%20">Ortsname</th>
                      <th width="%20">Stadt</th>
                      <th width="%40">Adresse</th>
                      <th width="%20">PLZ</th>
                      <th width="%20">Land</th>
                      </tr>
                  </thead>
                  <tfoot>
                    <tr>
                     <th width="%20">Ortsname</th>
                      <th width="%20">Stadt</th>
                      <th width="%40">Adresse</th>
                      <th width="%20">PLZ</th>
                      <th width="%20">Land</th>
                    </tr>
                  </tfoot>
                  <tbody>
                  <c:forEach items="${listOrt}" var="orte">
                   <tr>
                   	  <td>${orte.ortsname}</td>
                      <td>${orte.stadt}</td>
                      <td>${orte.adresse}</td>
                      <td>${orte.plz}</td>
                      <td>${orte.land}</td>
                      
                    </tr>
                  </c:forEach>
                   
                   
                  </tbody>
                </table>
              </div>
            </div>
          </div>

<%@ include file="../common/footer.jsp"%>
