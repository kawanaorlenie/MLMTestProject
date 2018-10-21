<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h3><spring:message code="signup.header" text="default text" /></h3>
<p>
   <spring:message code="signup.mainText" text="default text" />
</p>
<form:form class="signup_form" method="Post" action="signup" commandName="signUpModel">
   <fieldset>
      <legend>Registration</legend>    
      <form:errors path="*" class="alert alert-danger" /> 
      <label for="userName">User Name</label>
      <form:input class="signup" path="userName" />
      <label for="password">Password</label>
      <form:password class="signup" path="password" />
      <label for="confirmPassword">Confirm Password</label>
      <form:password class="signup" path="confirmPassword" />
      <label for="email">E-mail</label>
      <form:input class="signup" path="email" />
       <label class="with-checkbox"><input type="checkbox"><spring:message code="signup.accept"
            text="default text" /><a href=""> <spring:message code="signup.terms" text="default text" /></a></label>
      <button name="submit" type="submit">
         <spring:message code="signup.signUpButton" text="default text" />
      </button>
   </fieldset>
</form:form>
