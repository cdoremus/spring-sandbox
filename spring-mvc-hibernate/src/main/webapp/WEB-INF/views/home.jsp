<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Chirper</title>
    <link rel="stylesheet" 
          type="text/css" 
          href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
<%@ include file="header.jsp" %>    
  
    <h1><s:message code="spitter.welcome" text="Welcome to Chirper" /></h1>

    <s:url value="/chirpUser/register" var="registerUrl" />

    <a href="<s:url value="/chirps" />">Chirps</a> | 
    <a href="${registerUrl}">Register</a>

  </body>
</html>
