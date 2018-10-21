<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<link href="<c:url value="/resources/jquery-ui/css/custom-green/jquery-ui-1.10.4.custom.css" />" rel="stylesheet">
<link href="<c:url value="/resources/jtable/themes/lightcolor/green/jtable.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bootstrap/css/bootstrap.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/template/style.css" />" rel="stylesheet">
<tiles:insertAttribute name="css" ignore="true" />
<script src="<c:url value="/resources/jquery/jquery-1.11.1.js" />"></script>
<script src="<c:url value="/resources/jquery/jquery.cookie.js"/>"></script>
<script src="<c:url value="/resources/jquery/jquery.form.js"/>"></script>
<script src="<c:url value="/resources/jquery-ui/js/jquery-ui-1.10.4.custom.js"/>"></script>
<script src="<c:url value="/resources/js/template/template.js"/>"></script>
<tiles:insertAttribute name="js" ignore="true" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
   <div id="container">
      <div id="header" class="panel"><jsp:include page="header.jsp" /></div>
      <div id="main" class="panel">
         <tiles:insertAttribute name="jsp" ignore="true" />
      </div>
      <div id="footer" class="panel"><jsp:include page="footer.jsp" /></div>
   </div>
</body>
</html>
