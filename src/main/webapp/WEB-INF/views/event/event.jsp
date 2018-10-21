<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<div class="event panel">
			<div class="categories">Categories: 
				<c:forEach items="${EventDiv.e.category}" var="category">
				<a href="<c:url value="/user/categories/${category.categoryId }/events" />">${category.categoryName }</a>
				</c:forEach>
			</div>
			<div class="created">Created: ${EventDiv.e.registrationDate }</div>
			<div class="organizer">Organizer:  <a href="<c:url value="/user/profile/${EventDiv.e.organizer.userId }" />">${EventDiv.e.organizer.userName }</a></div>
			<div class="address">Events place: ${EventDiv.e.street},
				${EventDiv.e.city }, ${EventDiv.e.country}</div>
			<br>
			<div class="date">Events date: ${EventDiv.e.date.toString() }</div>
			<br>
			<div class="header">Header: ${EventDiv.e.header }</div>
			<br>
			<div class="descryption">Description: ${EventDiv.e.descryption}</div>
			<br>
			<div class="reliability">Organizers credibility: ${EventDiv.e.organizer.reliability}</div>
			<br>

			<div class="participants">
				Max number of participants: ${EventDiv.e.number_of_participants}<br>
				Currently assigned:<br>
				<c:set var="counter" value="0"/>
				<c:forEach items="${EventDiv.e.participants }" var="participant">
					<div class="user"><a href="<c:url value="/user/profile/${participant.user.userId }" />">${participant.user.userName}</a>
					
						<c:choose>
							<c:when test="${not participant.isConfirmed()}">- ${EventDiv.e.organizer.userName } confirmation needed
								<c:if test="${EventDiv.organizer}">
									<c:url var="confirm_url"  value="/user/event/${EventDiv.e.eventId}/confirm"/>
									<form:form method="Post" action="${confirm_url}"
										commandName="ConfirmForm${EventDiv.e.eventId}_${counter}">
										<form:hidden path="eventId"/>
										<form:hidden path="participantNumberOnTheList"/>
										<input type="submit" value="Confirm" />
									</form:form>
									<c:url var="reject_url"  value="/user/event/${EventDiv.e.eventId}/reject"/>
									<form:form method="Post" action="${reject_url }"
										commandName="ConfirmForm${EventDiv.e.eventId}_${counter}">
										<form:hidden path="eventId"/>
										<form:hidden path="participantNumberOnTheList"/>
										<input type="submit" value="Reject" />
									</form:form>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${EventDiv.organizer}">
									<c:url var="remove_url"  value="/user/event/${EventDiv.e.eventId}/remove"/>
									<form:form method="Post" action="${remove_url}"
										commandName="ConfirmForm${EventDiv.e.eventId}_${counter}">
										<form:hidden path="eventId"/>
										<form:hidden path="participantNumberOnTheList"/>
										<input type="submit" value="Remove" />
									</form:form>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
					<c:set var="counter" value="${counter + 1}"/>
				</c:forEach>
				<c:if test="${((not EventDiv.registered) and (not EventDiv.organizer))}">
					<c:url var="participate_url"  value="/user/event/${EventDiv.e.eventId}/participate"/> 
					<form:form method="Post" action="${participate_url }"
						commandName="ParticipateForm${EventDiv.e.eventId }">
						<form:hidden path="eventId" />
						<input type="submit" value="Participate" />
					</form:form>
				</c:if>
				
			</div>

	</div>
	
	<c:if test="${EventDiv.organizer}">
		<c:url var="cancel_url"  value="/user/event/${EventDiv.e.eventId}/cancel"/> 
		<form:form method="Post" action="${cancel_url }"
			commandName="CancelEventForm">
			<form:hidden path="eventId" />
			<input type="submit" value="Cancel Event" />
		</form:form>
	</c:if>
	
	<button id="post-message">post message</button>
	
	<div class="navigation">
		
		<a href="<c:url value="/user/categories/${categoryid}/events" />">back to events</a>
		
	</div>
	
	<div class="chat">
	<c:forEach items="${chat_messages}" var="message">
		<div class="ChatMessage panel">
			<div class="user">${message.user.userName }, </div>
			<div class="created">${message.postDate }: </div>
			<br>
			<div class="ChatMessage">${message.message } </div>
			<br>
		</div>
	</c:forEach>
	</div>

	
	
	<jsp:include page="postMessageDialog.jsp"></jsp:include>