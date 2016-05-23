<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Chirper</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
<%@ include file="header.jsp" %>    
    <div class="spittleForm">
      <h1>User: ${ chirpUser.firstName } ${ chirpUser.lastName }</h1>	
      <h1>Chirp something...</h1>
      <form method="POST" name="spittleForm">
        <input type="hidden" name="latitude">
        <input type="hidden" name="longitude">
        <textarea name="message" cols="80" rows="5"></textarea><br/>
        <input type="submit" value="Add" />
      </form>
    </div>
    <div class="listTitle">
      <h1>Recent Chirps</h1>
      <ul class="spittleList">
        <c:forEach items="${chirpList}" var="chirp" >
          <li id="spittle_<c:out value="chirp.id"/>">
            <div class="spittleMessage">
            	<a href="<c:url value="/chirps/${chirp.id}" />"><c:out value="${chirp.message}" /></a>
            </div>
            <div>
              <span class="spittleTime"><c:out value="${chirp.time}" /></span>
              <span class="spittleLocation">(<c:out value="${chirp.latitude}" />, <c:out value="${chirp.longitude}" />)</span>
            </div>
          </li>
        </c:forEach>
      </ul>
      <c:if test="${fn:length(chirpList) gt 20}">
        <hr />
        <s:url value="/chirps?count=${nextCount}" var="more_url" />
        <a href="${more_url}">Show more</a>
      </c:if>
    </div>
	<%@ include file="footer.jsp" %>    
  </body>
</html>