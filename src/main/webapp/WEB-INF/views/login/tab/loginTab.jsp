<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<h3>
   <spring:message code="login.header" text="default text" />
</h3>
<p>
   <spring:message code="login.mainText" text="default text" />
</p>
<form class="login_form" name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
   <FIELDSET>
      <LEGEND>Log In</LEGEND>
      <c:if test="${not empty error}">
         <div class="alert alert-danger">No User with this password exists.
            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
      </c:if>
      <label for="j_username">Login</label> <input type='text' placeholder="Login" name='j_username' id="j_username">
      <label for="j_password">Password</label> <input type='password' placeholder="Password" name='j_password'
         id="j_password" /> <label class="with-checkbox"> <input type="checkbox"
         name='_spring_security_remember_me' value="true" /> <spring:message code="login.rememberMe"
            text="default text" /></label>
      <button name="submit" type="submit">Log In</button>
   </FIELDSET>
</form>
