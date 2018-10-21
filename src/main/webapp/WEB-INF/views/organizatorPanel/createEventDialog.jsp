<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="createEventDialog" title="Create Event">
	<form:form id="createEventForm"
		class="jtable-dialog-form jtable-create-form" method="Post"
		action="${ pageContext.request.contextPath }/user/organizer/createEvent"
		commandName="EventForm" enctype="multipart/form-data">
		<%-- <form:hidden path="category" /> --%>
		<form:hidden path="EventId" />
		<form:hidden path="organizer" />
		<div class="jtable-input-field-container">Categories
		<ul id="expList">
 			<c:forEach items="${masterCategories}" var="masterCategory">
 				
    			<li class="collapsed">${masterCategory.name}:
    			<ul>
 				<c:forEach items="${masterCategory.categories}" var="category">
 					
        			<li>
 					<label><form:checkbox path="CategoriesSelected" value="${category.categoryId}"/>${category.categoryName}</label>
 					</li>
 					
 				</c:forEach>
 				</ul>
 				<li>
 				
 			</c:forEach>
 		</ul>
		</div>
		<div class="jtable-input-field-container">
			<div class="jtable-input-label">Event's date:</div>
			<div class="jtable-input jtable-text-input">
				<form:input path="date" />
			</div>
		</div>
	Event's localization <br>
		<div class="jtable-input-field-container">
			<div class="jtable-input-label">Street:</div>
			<div class="jtable-input jtable-text-input">
				<form:input path="Street" />
			</div>
		</div>
		<div class="jtable-input-field-container">
			<div class="jtable-input-label">City:</div>
			<div class="jtable-input jtable-text-input">
				<form:input path="City" />
			</div>
		</div>
		<div class="jtable-input-field-container">
			<div class="jtable-input-label">Country:</div>
			<div class="jtable-input jtable-text-input">
				<form:input path="Country" />
			</div>
		</div>   	
	Details <br>

		<div class="jtable-input-field-container">
			<div class="jtable-input-label">Header:</div>
			<div class="jtable-input jtable-text-input">
				<form:input path="Header" />
			</div>
		</div> 
Description <form:textarea path="Descryption" rows="5" cols="30" />
		<br>

		<div class="jtable-input-field-container">
			<div class="jtable-input-label">Number of participants:</div>
			<div class="jtable-input jtable-text-input">
				<form:input path="number_of_participants" />
			</div>
		</div> 
Confirm participants first <form:checkbox path="confirmParticipants" />
		<br>
		<input type="submit" value="Submit" />
	</form:form>
</div>