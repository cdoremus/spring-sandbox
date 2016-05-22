<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  <head>
    <title>Chirper</title>
    <link rel="stylesheet" 
          type="text/css" 
          href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
<%@ include file="header.jsp" %>
<h1>Show Chirp</h1>    
    <div class="spittleView">
      <div class="spittleMessage"><c:out value="${chirp.message}" /></div>
      <div>
        <span class="spittleTime"><c:out value="${chirp.time}" /></span>
      </div>
    </div>
<div style="padding-top:20px;">
	<a href="<c:url value="/chirps" />">Chirps</a>
</div>
    
<%@ include file="footer.jsp" %>    
  </body>
</html>