<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>awrank.com</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="http://jquery-json.googlecode.com/files/jquery.json-2.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/awrank.js"></script>

    <style type="text/css">
        h3 {
            background-color: #492248;
            color: #ffffff;
            padding: 3pt;
        }
    </style>

    <jsp:directive.include file="/WEB-INF/jsp/header.jspf"/>
</head>
<body onload="fIndexOnload();">

<h1>Welcome to the Awrank web application! <a href="index.html">[index.html]</a></h1>

<%-- twitter bootstrap color--%>
<h3>EMAIL</h3>

<p>
    <a id="testMailPage" href="mailtest">Main sending test pages</a>
</p>

<h3>API</h3>

<p>
    <a href="#" onclick="fTestJson()">Test json <> Object mapping </a> <br/>

    <!--a href="pages/dictionary/dictionary_list.jsp">Test dictionary</a> <br/ -->

<p>
<h5>Test /rest controllers</h5>
<ul>
    <li>
        <a href="rest/search?query=1">rest/search?query=1</a><br/>
    </li>
    <li>
        <a href="rest/dictionary/1">rest/dictionary/1</a><br/>
    </li>
    <li>
        <a href="rest/user/2">rest/user/2</a><br/>
    </li>
</ul>


</p>

</p>

<h3>LOGIN</h3>

<p>
    <a href="<c:url value="register"/>">Register</a> |
    <a href="<c:url value="login"/>">Login</a> |
    <a href="<c:url value="logout"/>">Logout</a> <br/>
 
</p>

<h3>Admin section</h3>
<p>
	<a href="<c:url value="admin/welcome"/>">Check access</a>
</p>
<hr/>
<p>
	<a href="<c:url value="admin/userlist"/>">Get all</a>
</p>	
<p>
	<a href="<c:url value="admin/userlistpage"/>">Get Page 0-30 (default)</a>
</p>	
<p>
	<a href="<c:url value="admin/userlistpage?page=0&page.size=2&page.sort=firstName&page.sort.dir=asc&isLogin=true"/>">Get Page 0-1 (with argument)</a>
</p>
<hr/>
<p>
<div height="100">
  <form method="POST" action="<c:url value="admin/user" />" accept="application/json" enctype="application/json">
   <table align="left"">
            
            <tr>
                <td align="right">Email*</td>
                <td><input type="text" name="email" value="user@awrank.com"/></td>
            </tr>
             <tr>
                <td colspan="2" align="right"><input type="submit" value="Search by email"/>
                    <input type="reset" value="Reset"/>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <br/>
    <br/>
    <br/>
</div>
</p>
<hr/>
<p>
<div  height="100">
  <form method="POST" action="<c:url value="admin/ip" />" accept="application/json" enctype="application/json">
   <table align="left">
            
            <tr>
                <td align="right">IP*</td>
                <td><input type="text" name="ip" value="0:0:0:0:0:0:0:1%0"/></td>
            </tr>
             <tr>
                <td colspan="2" align="right"><input type="submit" value="Search by IP"/>
                    <input type="reset" value="Reset"/>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <br/>
    <br/>
    <br/>
</div>  
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
        postJson('testJson', {dictionary:{id:15, language:"RU", code:"qwerty", text:"asdfg"}}, function (data) {
            console.log(data);
            alert(data);
        })
    }

</script>
</body>
</html>
