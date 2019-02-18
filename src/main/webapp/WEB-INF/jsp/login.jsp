<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<c:url var="rootURL" value="/"/>
<html>
<head>
<title>Login page</title>
</head>
<body>
<div class="col-md-6 col-md-offset-2">	
<c:if test="${param.error != null}">
             <div class="alert alert-danger">
                 Invalid UserName and Password.
             </div>
         </c:if>
         <c:if test="${param.logout != null}">
             <div class="alert alert-success">
                 You have been logged out.
             </div>
         </c:if>	
         </div>
            
     <div class="row">
<div class="col-md-6 col-md-offset-2">	
<h3>This login page is created for test purposes to explore REST services in browser</h3>
<form:form id="loginForm" method="post" action="${rootURL}login" modelAttribute="user"
class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">
<div class="form-group">
<label for="username" class="col-sm-2 control-label">UserName*</label>
<div class="col-sm-4">
<input type="text" id="username" name="username" class="form-control" placeholder="UserName" />
</div>
</div>
<div class="form-group">
<label for="password" class="col-sm-2 control-label">Password*</label>
<div class="col-sm-4">
<input type="password" id="password" name="password" class="form-control" placeholder="Password" />
</div>
</div>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-4">
<input type="submit" class="btn btn-primary" value="Login">
</div>
</div>

</form:form>
</div>
</div>


</body>
</html>