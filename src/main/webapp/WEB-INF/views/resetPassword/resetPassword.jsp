<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form:form method="Post" action="" commandName="resetPasswordModel">
   <fieldset>
      <legend>Enter your new password</legend>
      <form:password path="password" placeholder="Password" />
      <form:password path="confirmPassword" placeholder="Confirm Password" />
      <input type="submit" value="Submit" /> <br>
      <form:errors path="password" />
      <form:errors path="confirmPassword" />
   </fieldset>
</form:form>
