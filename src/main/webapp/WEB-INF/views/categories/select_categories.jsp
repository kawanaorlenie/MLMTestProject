<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select categories</title>
</head>
<body>
<h3>Select categories:</h3>
<div id="listContainer">

	<form:form method="Post" action="" commandName="Categories_Form">
		
		
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
 		
		<input type="submit" value="Submit" />
			
	</form:form>
	</div>
	<p><a href="<c:url value="/user/categories" />">Back</a></p>
	
</body>
</html>