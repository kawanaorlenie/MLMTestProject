<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="profileDialog" title="Edit Profile">
   <div class="jtable-input-field-container">
      <div class="jtable-input-label">First Name:</div>
      <div class="">${userProfile.firstName}</div>
   </div>
   <div class="jtable-input-field-container">
      <div class="jtable-input-label">Last Name:</div>
      <div class="">${userProfile.lastName}</div>
   </div>
   <div class="jtable-input-field-container">
      <div class="jtable-input-label">City:</div>
      <div class="">${userProfile.city}</div>
   </div>
   <div class="jtable-input-field-container">
      <div class="jtable-input-label">Birth date:</div>
      <div class="">
         <fmt:formatDate value="${userProfile.birthdate}" pattern="dd-MM-yyyy" />
      </div>
   </div>
   <div class="col-xs-6 col-md-3">
      <a href="#" class="thumbnail"> <img src="<c:url value="/avatar" />" alt=" Tekst alternatywny " />
      </a>
   </div>
</div>
