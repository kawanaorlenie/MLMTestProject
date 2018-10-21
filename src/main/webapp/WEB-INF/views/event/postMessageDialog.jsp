<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="postMessageDialog" title="Post Message">
	<form:form id="postMessageForm"
		class="jtable-dialog-form jtable-create-form" method="Post"
		action="${ pageContext.request.contextPath }/user/event/${EventDiv.e.eventId }/postMessage"
		commandName="ChatMessage" enctype="multipart/form-data">
		<form:hidden path="event" />
		<form:hidden path="user" />
		<form:hidden path="messageId" />
		<div class="jtable-input-field-container">
			<div class="jtable-input-label">Message:</div>
			<div class="jtable-input jtable-text-input">
				<form:textarea path="message" rows="5" cols="30" />
			</div>
		</div>
		<input type="submit" value="Send" />
	</form:form>
</div>