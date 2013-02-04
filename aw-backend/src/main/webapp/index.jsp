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

    <p>
        <h5>TestRestController</h5>
        <ul>
            <li>
                <a href="rest/search?query=1">rest/search?query=1</a><br/>
            </li>
            <li>
                <form action="rest/postjson" method="POST" accept="application/json" enctype="application/json">
                    Fill user data: <br/>
                    <label for="firstname">First name</label>
                    <input id="firstname" name="firstName" type="text"/><br/>

                    <label for="lastname">Last name</label>
                    <input id="lastname" name="lastName" type="text"/><br/>

                    <br/>
                    <input type="submit" value="Post">
                </form>
            </li>
            <li>
                <a href="rest/user/2">rest/user/2</a><br/>
            </li>
        </ul>


    </p>

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
