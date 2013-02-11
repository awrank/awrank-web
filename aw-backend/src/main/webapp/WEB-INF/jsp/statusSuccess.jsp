<%--
  Created by IntelliJ IDEA.
  User: andrewst
  Date: 11.02.13
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Status Success Page</title>
</head>
<body>
    <%
        if (request.getAttribute("Message") != null) {
            out.print(request.getAttribute("Message"));
        }
    %>
</body>
</html>