<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Chirper</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
    <h1>Your Profile</h1>
    <c:out value="${chirpUser.username}" /><br/>
    <c:out value="${chirpUser.firstName}" /> <c:out value="${chirpUser.lastName}" /><br/>
    <c:out value="${chirpUser.email}" />
  </body>
</html>
