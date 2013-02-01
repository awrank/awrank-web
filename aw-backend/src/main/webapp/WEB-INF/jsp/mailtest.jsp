<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" % -->
<!-- %@ taglib prefix="form" uri="http://www.springframework.org/tags/form"% -->
<%@ page session="true" %>
<html>
<head>
    <title>Sending email test page </title>
    <link href="/WEB-INF/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<h2> Mail test via SMTP: </h2>

<p>
    <b>SMTP host:</b> ${jsmpt_host_name} <br/>
    <b>SMTP port:</b> ${jsmpt_port} <br/>
    <b>SMTP username:</b> ${jsmpt_user_name} <br/>
    <b>SMTP password:</b> ${jsmpt_password}<br/>
    <br/>
    <b>Mail from address:</b> ${smpt_from_email}<br/>
    
    <b>Mail test activation email:</b> ${testactivation_email}<br/>
    <b>Mail test activation password:</b> ${testactivation_password}<br/>
</p>
<p>
    <a id="testSMTP" href=sendtestjungosmtp>Send test activation email via Jango SMTP</a>
</p>

<hr />

<h2> Mail test via Send Grid: </h2>

<p>
    <b>SMTP host:</b> ${sgsmpt_host_name} <br/>
    <b>SMTP port:</b> ${sgsmpt_port} <br/>
    <b>Mail username:</b> ${sgsmpt_user_name} <br/>
    <b>Mail password:</b> ${sgsmpt_password}<br/>
    <b>Mail from address:</b> ${smpt_from_email}<br/>
    
    <b>Mail test activation email:</b> ${testactivation_email}<br/>
    <b>Mail test activation password:</b> ${testactivation_password}<br/>
</p>
<p>
    <a id="testSMTP" href="sendtestsendgrid">Send test activation email via Send Grid</a>
</p>

</body>
</html>
