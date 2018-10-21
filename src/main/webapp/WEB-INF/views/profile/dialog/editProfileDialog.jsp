<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="editProfileDialog" title="Edit Profile">
   <form:form id="editProfileForm" class="jtable-dialog-form jtable-create-form" method="Post" action="profile/update"
      commandName="userProfile">
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">First Name:</div>
         <div class="jtable-input jtable-text-input">
            <form:input path="firstName" />
         </div>
      </div>
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">Last Name:</div>
         <div class="jtable-input jtable-text-input">
            <form:input path="lastName" />
         </div>
      </div>
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">City:</div>
         <div class="jtable-input jtable-text-input">
            <form:input path="city" />
         </div>
      </div>
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">Date:</div>
         <div class="jtable-input jtable-text-input">
            <form:input path="birthdate" />
         </div>
      </div>
   </form:form>
</div>
