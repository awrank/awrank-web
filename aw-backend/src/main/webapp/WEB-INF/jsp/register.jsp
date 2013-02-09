<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Register new user</title>
    <script type="text/javascript" src="../../resources/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/js/bootstrap/bootstrap.js"></script>
    <script type="text/javascript" src="../../resources/js/bootstrap/bootstrap-button.js"></script>
</head>
<body>

<div style="text-align: center;">
    <c:if test="${not empty error}">
    <!-- span style="color: red; ">
        <b>We did not recognize the username or password you entered.<br/>
            Please try again.</b><br/>
        <br/>
        If you don't remember your username or password, <a href="">click here</a> and <br/>
        we'll email you instructions to reset your password.<br/>
        <br/>
        <i>Spring message : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</i><br/>
        <br/>
    </span -->
    </c:if>
    <form method="POST" action="<c:url value="user/add" />">
        <table align="center" frame="hsides">
            <tr>
             
            </tr>
            <tr>
                <td align="right">Login</td>
                <td><input type="text" name="j_username"/></td>
            </tr>
            <tr>
                <td align="right">Email*</td>
                <td><input type="text" name="j_email"/></td>
            </tr>
            <tr>
                <td align="right">Password*</td>
                <td><input type="password" name="j_password"/></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" value="Register"/>
                    <input type="reset" value="Reset"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>