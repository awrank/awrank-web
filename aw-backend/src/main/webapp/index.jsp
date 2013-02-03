<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>awrank.com</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        h3 {
            background-color: #492248;
            color: #ffffff;
            padding: 3pt;
        }
    </style>
</head>
<body>

<h1>Welcome to the Awrank web application!</h1>

<%-- twitter bootstrap color--%>
<h3>EMAIL</h3>

<p>
    <a id="testMailPage" href="api/mailtest">Main sending test pages</a>
</p>

<h3>API</h3>
<p>
    <a href="api/testTransaction">Test transaction</a> <br/>
    <a href="pages/dictionary/dictionary_list.jsp">Test dictionary</a> <br/>
    <a href="rest/search?query=1">TestRestController</a><br/>
</p>

<h3>LOGIN</h3>
<p>
    <a href="<c:url value="/login"/>">Login</a> | <a href="<c:url value="/logout"/>">Logout</a> <br/>
</p>

<br/>
<h3>Session scope</h3>
${sessionScope}
<br/>

<br/>
<br/>

<p align="center" style="color: gray">
    <a href="http://awrank.com">Awrank</a>, January 2013
</p>
<br/>

<br/>
</body>
</html>
