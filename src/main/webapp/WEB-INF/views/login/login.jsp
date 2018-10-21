<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="tabs">
   <ul>
      <li><a href="#loginTab">Log In</a></li>
      <li><a href="#registerTab">Register</a></li>
      <li><a href="#passwordRecoveryTab">Password Recovery</a></li>
   </ul>
   <div id="loginTab" class="tab">
      <jsp:include page="tab/loginTab.jsp" />
   </div>
   <div id="registerTab" class="tab">
      <jsp:include page="tab/registerTab.jsp" />
   </div>
   <div id="passwordRecoveryTab" class="tab">
      <jsp:include page="tab/passwordRecoveryTab.jsp" />
   </div>
</div>
<c:if test="${not empty signUpSuccess}">
   <script>
				$(document).ready(function() {
					$("#registrationSuccessDialog").dialog();
				});
			</script>
   <jsp:include page="dialog/registrationSuccessDialog.jsp"></jsp:include>
</c:if>
<c:if test="${not empty activationSuccess}">
   <c:if test="${activationSuccess=='true'}">
      <script>
							$(document).ready(function() {
								$("#activationSuccessDialog").dialog();
							});
						</script>
      <jsp:include page="dialog/activationSuccessDialog.jsp"></jsp:include>
   </c:if>
   <c:if test="${activationSuccess=='false'}">
      <script>
							$(document).ready(function() {
								$("#activationFailDialog").dialog();
							});
						</script>
      <jsp:include page="dialog/activationFailDialog.jsp"></jsp:include>
   </c:if>
</c:if>
<c:if test="${not empty forgotPasswordSuccess}">
   <script>
				$(document).ready(function() {
					$("#forgotPasswordSuccess").dialog();
				});
			</script>
   <jsp:include page="dialog/forgotPasswordSuccess.jsp"></jsp:include>
</c:if>
