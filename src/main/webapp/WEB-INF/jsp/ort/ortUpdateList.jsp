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
                      <th>Ortsname</th>
                      <th>Stadt</th>
                      <th>Adresse</th>
                      <th>PLZ</th>
                      <th>Land</th>
                      <th>Update</th>
                      </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Ortsname</th>
                      <th>Stadt</th>
                      <th>Adresse</th>
                      <th>PLZ</th>
                      <th>Land</th>
                      <th>Update</th>
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
                      <td><a href="updateort/${orte.id}" class="btn btn-dark" role="button">UPDATE</a></td>
                      
                    </tr>
                  </c:forEach>
                   
                   
                  </tbody>
                </table>
                
              </div>
            </div>
          </div>

<%@ include file="../common/footer.jsp"%>
