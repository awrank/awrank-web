<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>awrank.com</title>

	<jsp:directive.include file="/WEB-INF/jsp/header.jspf"/>

	<link href="${resources}/main.css" rel="stylesheet" type="text/css"/>

	<style type="text/css">
		h3 {
			background-color: #492248;
			color: #ffffff;
			padding: 3pt;
		}
	</style>


</head>
<body>

<h1>Welcome to the Awrank web application! <a href="index.html">[index.html]</a></h1>

<%-- twitter bootstrap color--%>
<h3>EMAIL</h3>

<p>
	<a id="testMailPage" href="mailtest">Main sending test pages</a>
</p>

<h3>API</h3>

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
	<li>
		<a href="rest/profile/accesshistory">rest/profile/accesshistory</a><br/>
	</li>
</ul>


</p>
</p>
<h3>User Profile</h3>

<p>
	<a href="<c:url value="rest/profile/accesshistory"/>">My last 100 login (you have to be logged in)</a>
</p>

<p>
	<a href="<c:url value="rest/profile/accesshistory?page=0&page.size=50&page.sort=signinDate&page.sort.dir=asc&isLogin=true"/>">My
		last 50 login (you have to be logged in)</a>
</p>

<p>
	<a href="<c:url value="rest/profile/accesshistory?page=0&page.size=10&page.sort=signinDate&page.sort.dir=asc&isLogin=true"/>">My
		last 10 login (you have to be logged in)</a>
</p>
<hr/>
<p>

<div height="100">
	<form method="POST" action="<c:url value="rest/profile/resetpassword" />" accept="application/json"
		  enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">Email*</td>
				<td><input type="text" name="email" value="okorokhina@gmail.com"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Reset password (send link to email)"/>
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
<p> Change email - enter new, you have to be logged in </p>

<p>

<div height="100">
	<form method="POST" action="<c:url value="rest/profile/changeemailmanual" />" accept="application/json"
		  enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">New Email*</td>
				<td><input type="text" name="email" value="okorokhina@gmail.com"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Reset current email with new one"/>
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
<p> Change password - enter current and new </p>

<p>

<div style="text-align: center;">
	<table align="left" cellspacing="0" border="0">
		<tr>
			<td colspan="2" align="left"><h2>Change your password (you have to be logged in):</h2></td>
		</tr>

		<form method="POST" action="<c:url value="/rest/profile/changepasswordmanual" />" accept="application/json"
			  enctype="application/json">
			<tr>
				<td colspan="2">
					Current password: <input type="password" name="currentPassword" style="width: 50%"/>
				</td>
			</tr>
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
</p>


<h3>LOGIN</h3>

<p>
	<a href="<c:url value="register"/>">Register</a> |
	<a href="<c:url value="logout"/>">Logout</a> <br/>
</p>

<h3>Admin section</h3>

<p>
	<a href="<c:url value="admin/welcome"/>">Check access</a>
</p>
<hr/>
<p>
	<a href="<c:url value="admin/userlist"/>">Get all users</a>
</p>

<p>
	<a href="<c:url value="admin/userlistpage"/>">Get users, Page 0-30 (default)</a>
</p>

<p>
	<a href="<c:url value="admin/userlistpage?page=0&page.size=2&page.sort=firstName&page.sort.dir=asc&isLogin=true"/>">Get
		users,
		Page 0-1 (with argument)</a>
</p>
<hr/>
<p>

<div height="100">
	<form method="POST" action="<c:url value="admin/user" />" accept="application/json" enctype="application/json">
		<table align="left">

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

<div height="100">
	<form method="POST" action="<c:url value="admin/ip" />" accept="application/json" enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">IP*</td>
				<td><input type="text" name="ip" value="0:0:0:0:0:0:0:1%0"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Search users by IP"/>
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

<div height="100">
	<form method="POST" action="<c:url value="admin/userentryhistory" />" accept="application/json"
		  enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">Email*</td>
				<td><input type="text" name="email" value="user@awrank.com"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Get EntryPoints for user"/>
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
<div height="100">
	<form method="POST" action="<c:url value="admin/useriphistory" />" accept="application/json"
		  enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">Email*</td>
				<td><input type="text" name="email" value="user@awrank.com"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Get Ips for user"/>
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
<p>

<div height="100">
	<form method="POST" action="<c:url value="admin/blockuserbyemail" />" accept="application/json"
		  enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">Email*</td>
				<td><input type="text" name="email" value="okorokhina@gmail.com"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Block user by email"/>
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
<p>

<div height="100">
	<form method="POST" action="<c:url value="admin/blockuserbyid" />" accept="application/json"
		  enctype="application/json">
		<table align="left">

			<tr>
				<td align="right">Id*</td>
				<td><input type="text" name="id" value="4"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Block user by id"/>
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

</script>
</body>
</html>
