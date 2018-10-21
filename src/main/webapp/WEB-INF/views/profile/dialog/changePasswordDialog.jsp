<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="changePasswordDialog" title="Edit Profile">
   <form:form id="changePasswordForm" class="jtable-dialog-form jtable-create-form" method="Post"
      action="profile/changePassword" commandName="changePasswordModel">
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">Old password:</div>
         <div class="jtable-input jtable-text-input">
            <form:password path="oldPassword" />
            <form:errors path="oldPassword" />
         </div>
      </div>
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">New password:</div>
         <div class="jtable-input jtable-text-input">
            <form:password path="newPassword" />
            <form:errors path="newPassword" />
         </div>
      </div>
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">Confirm password:</div>
         <div class="jtable-input jtable-text-input">
            <form:password path="confirmPassword" />
            <form:errors path="confirmPassword" />
         </div>
      </div>
   </form:form>
</div>
