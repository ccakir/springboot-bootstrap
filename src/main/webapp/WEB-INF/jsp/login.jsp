<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">

  
</head>
<body>

  <div class="container" style="width: 500px; padding-top: 50px">
     
     
 <form action="login" class="was-validated" method="post">
  <div class="form-group ${error != null ? 'has-error' : ''}">
    <label for="username">Email:</label>
    <input type="text" class="form-control" id="username" placeholder="Email" name="username" required>
    
  </div>
  <div class="form-group ${error != null ? 'has-error' : ''}">
    <label for="password">Kennwort:</label>
    <input type="password" class="form-control" id="password" placeholder="Kennwort" name="password" required>
    <span>${error}</span>
    
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </div>
  
  <button type="submit" class="btn btn-primary">Login</button>
  
  
</form>  
    </div>
 
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  
</body>
</html>