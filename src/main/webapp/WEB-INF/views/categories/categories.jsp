<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
<c:forEach items="${categories_shown}" var="category" varStatus="block">
  <div class="col-xs-6 col-sm-4">
    <a href="<c:url value="/user/categories/${category.categoryId}/events"/>" class="thumbnail">
      <img src="<c:url value="${category.categoryIconPath}" />" alt="${category.categoryName}" class="img-thumbnail">
    </a>
  </div>
</c:forEach>
</div>

<div class="navigation">
   <c:if test="${page>1}">
      <div class="button">
         <p>
            <a href="<c:url value="/user/categories/${page-1}" />">previous page</a>
         </p>
      </div>
   </c:if>
   <c:if test="${not lastpage}">
      <div class="button">
         <p>
            <a href="<c:url value="/user/categories/${page+1}" />">next page</a>
         </p>
      </div>
   </c:if>
   <div class="button">
      <p>
         <a href="<c:url value="/user/categories/edit" />">category selection</a>
      </p>
   </div>
</div>
