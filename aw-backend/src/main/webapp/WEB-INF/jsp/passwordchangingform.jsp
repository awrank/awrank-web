<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Login page</title>

    <style type="text/css">
        td {
            text-align: left;
            padding-left: 5px;
            padding-right: 5px;
        }
    </style>
</head>
<body>

<div style="text-align: center;">
    <table align="center" cellspacing="0" border="0">
        <tr>
            <td colspan="2" align="left"><h2>Change your password:</h2></td>
        </tr>
        
        <form method="POST" action="<c:url value="/rest/profile/newpassword" />"  accept="application/json" enctype="application/json">
            <tr>
                <td colspan="2">
                    New password: <input type="password" name="password" style="width: 50%"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    Confirm: <input type="password" name="passwordConfirm" style="width: 50%"/>
                </td>
            </tr>
            <tr align="right">
                <td colspan="2" align="right" style="padding-top: 20px;">
                    <input type="submit" id="submit" align="right"/>
                </td>
            </tr>
        </form>
        
    </table>
</div>

</body>
</html>