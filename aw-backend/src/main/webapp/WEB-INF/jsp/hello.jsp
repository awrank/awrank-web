<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h3>Hello, ${username}!</h3>

<h3>You granted with such roles:</h3>
<c:forEach var="role" items="${authorities}">
        ${role.authority} <br/>
</c:forEach>

<br/>
<a href="rest/search?query=1">TestRestController</a>
<br/>

<br/>
<a href="<c:url value="/logout"/>"> Logout</a>

</body>
</html>