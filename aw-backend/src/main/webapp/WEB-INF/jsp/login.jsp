<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Login page</title>
    <%--<jsp:directive.include file="header.jspf"/>--%>
</head>
<body>

<%--<a href="<c:url value="/index" />">--%>
<%--<spring:message code="label.contacts"/>--%>
<%--</a><br/>--%>

<div style="text-align: center;">
    <c:if test="${not empty error}">
    <span style="color: red; ">
        <b>We did not recognize the username or password you entered.<br/>
            Please try again.</b><br/>
        <br/>
        If you don't remember your username or password, <a href="">click here</a> and <br/>
        we'll email you instructions to reset your password.<br/>
        <br/>
        <i>Spring message : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</i><br/>
        <br/>
    </span>
    </c:if>

    <div>
        <form action="signin/google" method="POST">
            <button type="submit" class="btn btn-large btn-primary">Sign in with Google</button>
            <input type="hidden" name="scope"
                   value="https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo#email https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/tasks https://www-opensocial.googleusercontent.com/api/people https://www.googleapis.com/auth/drive"/>
        </form>
    </div>

    <form method="POST" action="<c:url value="/j_spring_security_check" />">
        <table align="center" frame="hsides">
            <tr>
                <td align="right">Email</td>
                <td><input type="text" name="j_username"/></td>
            </tr>
            <tr>
                <td align="right">Password</td>
                <td><input type="password" name="j_password"/></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" value="Login"/>
                    <input type="reset" value="Reset"/>
                </td>
            </tr>
        </table>
    </form>
    <a href="<c:url value="register"/>">Register</a>
</div>

<div>
    <table cellpadding="10" cellspacing="10" align="center">
        <tr>
            <td colspan="8"><h3 align="center">Welcome to Social Auth Demo</h3></td>
        </tr>
        <tr>
            <td colspan="8"><p align="center">Please click on any icon.</p></td>
        </tr>
        <tr>
            <td>
                <a href="socialauth?id=facebook">Facebook</a>
            </td>
            <td>
                <a href="socialauth?id=google">Google</a>
            </td>
        </tr>
        <tr>
            <td colspan="8" align="center">
                <form action="socialauth" <%--onsubmit="return validate(this);--%>">
                    or enter OpenID url: <input type="text" value="" name="id"/>
                    <input type="submit" value="Submit"/>
                </form>
            </td>
        </tr>
    </table>
</div>

</body>
</html>