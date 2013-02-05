<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/spring-social/social/tags" prefix="social" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Title</title>
    <!-- jQuery required for Facebook connect form -->
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
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
    <form method="POST" action="<c:url value="/j_spring_security_check" />">
        <table align="center" frame="hsides">
            <tr>
                <td>
                    <button>Sign in with Google</button>
                </td>
                <td>
                    <!--  FACEBOOK -->
                    <c:if test="${connectedToFacebook}">
                        Connected to Facebook as ${facebookProfile.firstName } ${facebookProfile.lastName }
                    </c:if>
                    <c:if test="${!connectedToFacebook}">
                        <%/* Connect to Facebook */ %>
                        <form id="fb_signin" action="<c:url value="/callback/fb" />" method="post">
                            <div class="formInfo">
                            </div>
                            <div id="fb-root"></div>
                            <p><fb:login-button perms="email,publish_stream,offline_access"
                                                onlogin="$('#fb_signin').submit();"
                                                v="2"
                                                length="long">Connect to Facebook</fb:login-button></p>
                        </form>

                        <social:connected provider="facebookProvider"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td align="right">Login</td>
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
</div>
</body>
</html>