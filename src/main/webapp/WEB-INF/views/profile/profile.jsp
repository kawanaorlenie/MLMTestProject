<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="buttons-container">
   <button id="edit-profile">Edit profile</button>
   <button id="changePassword">Change Password</button>
   <button id="uploadPhoto">Upload Photo</button>
</div>
<div class="profile-container ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons">
   <div class="ui-dialog-titlebar ui-widget-header ui-corner-all">Profile</div>
   <div class="table-container">
      <label>First Name:</label>
      <h4 id="hFirstName">${userProfile.firstName}</h4>
      <label>Last Name:</label>
      <h4 id="hLastName">${userProfile.lastName}</h4>
      <label>City:</label>
      <h4 id="hCity">${userProfile.city}</h4>
      <label>Birth date:</label>
      <h4 id="hBirthdate">
         <fmt:formatDate value="${userProfile.birthdate}" pattern="dd-MM-yyyy" />
      </h4>
   </div>
   <div class="photo-container">
      <img src="<c:url value="/avatar" />" alt=" Tekst alternatywny " class="img-thumbnail" />
   </div>
</div>
<jsp:include page="dialog/editProfileDialog.jsp"></jsp:include>
<jsp:include page="dialog/changePasswordDialog.jsp"></jsp:include>
<jsp:include page="dialog/uploadPhotoDialog.jsp"></jsp:include>