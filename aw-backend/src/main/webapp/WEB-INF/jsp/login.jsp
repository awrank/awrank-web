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
    <style type="text/css">
        td {
            text-align: left;
            padding-left: 5px;
            padding-right: 5px;
        }
    </style>
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

    <c:if test="${isError eq true}">
        <b>Oops! Something goes wrong: ${errorMessage}</b>
    </c:if>

    <table align="center" cellspacing="0" border="0">
        <tr>
            <td colspan="2" align="left"><h2>Log in to your account</h2></td>
        </tr>
        <tr bgcolor="aqua">
            <td align="left"><b>with social network:</b></td>
            <td>&nbsp;</td>
        </tr>
        <tr bgcolor="aqua">
            <td>
                <div>
                    <form action="loginViaGoogle" method="GET">
                        <button type="submit" class="btn btn-large btn-primary">Log in with Google</button>
                    </form>
                </div>
            </td>
            <td>
                <div>
                    <form action="socialauth?id=facebook" method="POST">
                        <button type="submit" class="btn btn-large btn-primary">Log in with Facebook</button>
                    </form>
                </div>
            </td>
        </tr>
        <tr>
            <td style="padding-top: 20px;"><b>or with registered details:</b></td>
            <td>&nbsp;</td>
        </tr>
        <form method="POST" action="<c:url value="/j_spring_security_check" />">
            <tr>
                <td colspan="2">
                    Email: <input type="text" name="j_username" style="width: 50%"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    Password: <input type="password" name="j_password" style="width: 50%"/>
                </td>
            </tr>
            <tr align="right">
                <td colspan="2" align="right" style="padding-top: 20px;">
                    <input type="submit" id="submit" align="right"/>
                </td>
            </tr>
        </form>
        <tr>
            <td style="padding-top: 20px;"><a href="<c:url value="forgot"/>">Forgotten your password?</a></td>
            <td style="padding-top: 20px;">Don't have an account? <a href="<c:url value="register"/>">Register</a></td>
        </tr>
    </table>


</div>

<%-- Social Auth Demo--%>
<div style="visibility: hidden;">
    <hr/>
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
                <form action="socialauth"
                <%--onsubmit="return validate(this);--%>">
                or enter OpenID url: <input type="text" value="" name="id"/>
                <input type="submit" value="Submit"/>
                </form>
            </td>
        </tr>
    </table>
</div>

</body>
</html>