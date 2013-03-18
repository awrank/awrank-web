<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="true" %>
<html>
<head>
    <title>Account data page </title>
    <link href="/WEB-INF/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body>

<h2> Change your account data: </h2>

<form name="frm1" method="post" action="<c:url value="/rest/profile/userdata/update" />"  accept="application/json" enctype="application/json">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="22%">&nbsp;</td>
    <td width="78%">&nbsp;</td>
    </tr>
  <tr>
    <td> <b>First Name:</b>  <br/> </td>
    <td><input type="text" name="firstName" value="${userdata.firstName}"></td><!--  "${userdata.getFirstName()}" -->
  </tr>
  <tr>
    <td> <b>Last Name:</b>  <br/> </td>
    <td><input type="text" name="lastName" value="${userdata.lastName}"></td>
  </tr>
    <tr>
        <td><b>Language(EN, RU)</b></td>
        <td><input type="language" name="language" value="${userdata.language}"/></td>
    </tr>
    <tr>
       <td><b>Birthday date</b></td>
        <td><input type="text" name="birthdayAsFormattedString" value="${userdata.birthdayAsFormattedString}"/></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="submit" value="Update my data"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="button" value="Cancel" onclick="history.go(-1)"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="hidden" name="userId" value="${userdata.userId}"></td></td>
    </tr>
</table>
</form>
</body>
</html>
