<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>awrank.com</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="http://jquery-json.googlecode.com/files/jquery.json-2.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/awrank.js"></script>

    <style type="text/css">
        h3 {
            background-color: #492248;
            color: #ffffff;
            padding: 3pt;
        }
    </style>
</head>
<body onload="fIndexOnload();">

<h1>Welcome to the Awrank web application!</h1>

<%-- twitter bootstrap color--%>
<h3>EMAIL</h3>

<p>
    <a id="testMailPage" href="api/mailtest">Main sending test pages</a>
</p>

<h3>API</h3>

<p>
    <a href="#" onclick="fTestJson()">Test json with TestJsonInput</a> <br/>
    <a href="#" onclick="fTestJson1()">Test json with TestJsonInput without nested "data" object</a> <br/>
    <a href="#" onclick="fTestJson2()">Test json with String</a> <br/>
    <a href="pages/dictionary/dictionary_list.jsp">Test dictionary</a> <br/>

    <p>
        <h5>TestRestController</h5>
        <ul>
            <li>
                <a href="api/rest/search?query=1">api/rest/search?query=1</a><br/>
            </li>
            <li>
                <form action="api/rest/postjson" method="POST" accept="application/json" enctype="application/json">
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
                <a href="api/rest/user/2">api/rest/user/2</a><br/>
            </li>
        </ul>


    </p>

</p>

<h3>LOGIN</h3>

<p>
    <a href="<c:url value="/api/login"/>">Login</a> | <a href="<c:url value="/api/logout"/>">Logout</a> <br/>
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

<script type="text/javascript" language="JavaScript">
    function fIndexOnload() {
        setContextPath('<%=request.getContextPath()%>');
    }

    function fTestJson() {
        postJson('testJson', {dictionary: {id: 15, language: "RU", code: "qwerty", text: "asdfg"}}, function (data) {
            console.log(data);
            alert(data);
        })
    }
    
     function fTestJson1() {
        postJson2('testJson', {dictionary: {id: 15, language: "RU", code: "qwerty", text: "asdfg"}}, function (data) {
            console.log(data);
            alert(data);
        })
    }
      function fTestJson2() {
        postJson('testJson2', {dictionary: {id: 15, language: "RU", code: "qwerty", text: "asdfg"}}, function (data) {
            console.log(data);
            alert(data);
        })
    }
</script>
</body>
</html>
