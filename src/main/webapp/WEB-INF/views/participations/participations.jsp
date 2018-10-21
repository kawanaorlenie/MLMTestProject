<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- organizatorsPanelControler -->
	<div class="navigation">
		<p>
			<button id="create-event">create event</button>
		</p>
		
	</div>

	<h1>PANEL UCZESTNIKA</h1>
	<br>
	Wydarzenia w których bierzesz udział:<br>
	<c:forEach items="${events}" var="event">
		<div class="panel event">
			<div class="categories">Categories: 
				<c:forEach items="${event.category}" var="category">
				<a href="<c:url value="/user/categories/${category.categoryId }/events" />">${category.categoryName }</a>
				</c:forEach>
			</div>
			<div class="created">Created: ${event.registrationDate }</div>
			<div class="organizer">Organizer: <a href="<c:url value="/user/profile/${event.organizer.userId }" />">${event.organizer.userName }</a></div>
			<div class="address">Events place: ${event.street},
				${event.city }, ${event.country}</div>
			<br>
			<div class="date">Events date: ${event.date.toString() }</div>
			<br>
			<div class="header">Header: ${event.header }</div>
			<br>
			<div class="descryption">Description: ${event.descryption}</div>
			<br>
			<div class="reliability">Organizers credibility: ${event.organizer.reliability}</div>
			<br>

			<div class="participants">
				Max number of participants: ${event.number_of_participants}<br>
				Currently assigned:<br>
				<c:set var="counter" value="0"/>
				<c:forEach items="${event.participants }" var="participant">
					<div class="user"><a href="<c:url value="/user/profile/${participant.user.userId }" />">${participant.user.userName}</a>
					<c:if test="${not participant.isConfirmed()}">- ${event.organizer.userName } confirmation needed
					
					</c:if>
					</div>
					<br>
					
				</c:forEach>
				
			</div>
			<br>
			<div class="eventLink"><a href="<c:url value="/user/event/${event.eventId}" />">Show event</a></div>
			<br>

		</div>
		<br>
	</c:forEach>
