<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" % -->
<%@ page session="true" %>
<html>
<head>
    <title>Sending email test page </title>
    <link href="/WEB-INF/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<h2> Mail test </h2>

<p>
    <b>SMTP host:</b> ${smpt_host_name} <br/>
    <b>SMTP port:</b> ${smpt_port} <br/>
    <b>Mail username:</b> ${smpt_user_name} <br/>
    <b>Mail password:</b> ${smpt_password}<br/>
</p>
</body>
</html>
