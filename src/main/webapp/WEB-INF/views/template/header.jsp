<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class=" hello-user">
   <sec:authorize url="/user">
			Hello, <sec:authentication property="principal.username" />!
   </sec:authorize>
   <sec:authorize url="/">
         You are not logged in. 
   </sec:authorize>
</div>
<div class=" menu">
   <sec:authorize url="/user">
      <ul>
         <li><a href="<c:url value="/user/categories" />">Categories</a></li>
         <li><a href="<c:url value="/user/organizer" />">Organizer Panel</a></li>
         <li><a href="<c:url value="/user/participations" />">Participations</a></li>
         <li><a href="<c:url value="/user/profile" />">Profile</a></li>
         <li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
         <c:forEach items="${user.notifications}" var="notification">
			<li><a href="<c:url value="${notification.link }" />">${notification.getNotificationType().getMessage() }</a></li>
		 </c:forEach>
         <sec:authorize url="/admin">
            <li><a href="<c:url value="/admin" />">Admin Panel</a></li>
         </sec:authorize>
      </ul>
   </sec:authorize>
</div>
<div class=" language">
   <!--   Language: <a href="?language=en">English</a> <a href="?language=pl">Polski</a> -->
   <label>Language: </label> <select id="languageSelect" class="ui-button ui-widget ui-state-default ui-corner-all">
      <option value="en">English</option>
      <option value="pl">Polski</option>
   </select>
</div>
