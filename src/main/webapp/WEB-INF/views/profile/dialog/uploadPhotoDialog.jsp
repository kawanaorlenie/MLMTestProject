<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="uploadPhotoDialog" title="Upload Photo">
   <form:form id="uploadPhotoForm" class="jtable-dialog-form jtable-create-form" method="Post"
      action="profile/update/photo" enctype="multipart/form-data">
      <div class="jtable-input-field-container">
         <div class="jtable-input-label">Photo:</div>
         <div class="jtable-input jtable-text-input">
            <input type="file" id="file" name="file" style="display: none" accept="image/png,image/jpeg" /> <input
               type="text" id="fileName" />
         </div>
      </div>
      <button id="upload" type="submit" style="visibility:hidden">Upload</button>
   </form:form>
</div>
