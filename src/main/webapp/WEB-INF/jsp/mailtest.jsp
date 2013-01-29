<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" % -->
<%@ page session="true" %>
<html>
<head>
    <title>Sending email test page </title>
</head>
<body>

<c:out value="${smpt_host_name}"/></br>
<c:out value="${smpt_port}"/></br>
<c:out value="${smpt_user_name}"/></br>
<c:out value="${smpt_password}"/></br>
</body>
</html>
