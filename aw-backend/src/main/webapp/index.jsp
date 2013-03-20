<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>awrank.com : debug page</title>
	<link rel="shortcut icon" href="resources/img/favicon.ico">

	<jsp:directive.include file="/WEB-INF/jsp/header.jspf"/>

	<style type="text/css">
		body {
			padding-top: 60px;
			padding-bottom: 40px;
		}

		.sidebar-nav {
			padding: 9px 0;
		}
	</style>

</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="brand" href="#">AwRank</a>
			<ul class="nav pull-left">
				<li class="text-info span2">
					<a href="index.html">Go to Frontend</a>
				</li>
			</ul>
		</div>
	</div>
</div>

<div name="alert-success"></div>

<div class="container-fluid">

<h2>Debug Area</h2>

<div class="tabbable tabs-left">
<ul class="nav nav-tabs">
	<li class="active"><a href="#tabSession" data-toggle="tab">Session</a></li>
	<li><a href="#tabLogin" data-toggle="tab">Login</a></li>
	<li><a href="#tabEmail" data-toggle="tab">Email</a></li>
	<li><a href="#tabAPI" data-toggle="tab">API</a></li>
	<li><a href="#tabUserProfile" data-toggle="tab">User Profile</a></li>
	<li><a href="#tabAdminSection" data-toggle="tab">Admin section</a></li>
	<hr/>
	<li><a href="#tabKnownIssues" data-toggle="tab" class="muted">Known issues</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tabSession">
	<div class="well">
		<fieldset>
			<legend>Session scope</legend>
			${sessionScope}
		</fieldset>
	</div>
</div>

<div class="tab-pane" id="tabLogin">
	<div class="well">
		<fieldset>
			<legend>Actions connected to authentication</legend>
			<ul>
				<li><a href="<c:url value="login"/>">Login</a></li>
				<li><a href="<c:url value="register"/>">Register</a></li>
				<li><a href="<c:url value="logout"/>">Logout</a></li>
			</ul>
		</fieldset>
	</div>
</div>

<div class="tab-pane" id="tabEmail">
	<div class="well">
		<fieldset>
			<legend>Email test page</legend>
			<a id="testMailPage" href="mailtest">Go to the test page</a>
		</fieldset>
	</div>
</div>

<div class="tab-pane" id="tabAPI">
	<div class="well">
		<fieldset>
			<legend>Test /rest controllers</legend>
			<ul>
				<li>
					<a href="rest/search?query=1">rest/search?query=1</a>
				</li>
				<li>
					<a href="rest/dictionary/1">rest/dictionary/1</a>
				</li>
				<li>
					<a href="rest/user/2">rest/user/2</a><br/>
				</li>
				<li>
					<a href="rest/profile/accesshistory">rest/profile/accesshistory</a><br/>
				</li>
			</ul>
		</fieldset>
	</div>
</div>

<div class="tab-pane" id="tabUserProfile">
	<div class="well">
		<fieldset>
			<legend>Rest methods</legend>
			<ul>
				<li><a href="<c:url value="rest/profile/userdata/get"/>">Go to "Change my data" form (name
					etc.
					you
					have to be logged in)</a></li>
				<li><a href="<c:url value="rest/profile/accesshistory"/>">My last 100 login (you have to be
					logged
					in)</a></li>
				<li>
					<a href="<c:url value="rest/profile/accesshistory?page=0&page.size=50&page.sort=signinDate&page.sort.dir=asc&isLogin=true"/>">My
						last 50 login (you have to be logged in)</a></li>
				<li>
					<a href="<c:url value="rest/profile/accesshistory?page=0&page.size=10&page.sort=signinDate&page.sort.dir=asc&isLogin=true"/>">My
						last 10 login (you have to be logged in)</a></li>
			</ul>
		</fieldset>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="rest/profile/resetpassword" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Reset password</legend>
				<label>Email*</label>
				<input type="text" name="email" placeholder="Type email" value="okorokhina@gmail.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Reset password (send link to email)</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="rest/profile/changeemailmanual" />"
			  accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Change email (Enter a new own, you have to be logged in)</legend>
				<label>New Email*</label>
				<input type="text" name="email" placeholder="Type email" value="okorokhina@gmail.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Reset current email with new one</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="/rest/profile/changepasswordmanual" />"
			  accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Change password (enter current and new)</legend>
				<input type="password" name="currentPassword" placeholder="Current password">
				<input type="password" name="Password" placeholder="New password">
			</fieldset>
			<button id="submit" type="submit" class="btn">Reset current email with new one</button>
		</form>
	</div>
</div>

<div class="tab-pane" id="tabAdminSection">
	<div class="well">
		<fieldset>
			<legend>Test /rest controllers</legend>
			<ul>
				<li><a href="<c:url value="admin/welcome"/>">Check access</a></li>
				<li><a href="<c:url value="admin/userlist"/>">Get all users</a></li>
				<li><a href="<c:url value="admin/userlistpage"/>">Get users, Page 0-30 (default)</a></li>
				<li>
					<a href="<c:url value="admin/userlistpage?page=0&page.size=2&page.sort=firstName&page.sort.dir=asc&isLogin=true"/>">Get
						users, Page 0-1 (with argument)</a></li>
			</ul>
		</fieldset>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/user" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Search by email</legend>
				<label>Email*</label>
				<input type="text" name="email" placeholder="Type email" value="user@awrank.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Search</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/ip" />" accept="application/json" enctype="application/json">
			<fieldset>
				<legend>Search by IP address</legend>
				<label>IP*</label>
				<input type="text" name="ip" placeholder="Type IP address" value="0:0:0:0:0:0:0:1%0">
				<span class="help-block">E.g. 0:0:0:0:0:0:0:1%0</span>
				<button type="submit" class="btn">Search</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/userentryhistory" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Get EntryPoints for user</legend>
				<label>Email*</label>
				<input type="text" name="email" placeholder="Type email" value="user@awrank.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Get</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/useriphistory" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Get IPs for user</legend>
				<label>Email*</label>
				<input type="text" name="email" placeholder="Type email" value="user@awrank.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Get</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/blockuserbyemail" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Block user by email</legend>
				<label>Email*</label>
				<input type="text" name="email" placeholder="Type email" value="user@awrank.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Block</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/blockuserbyid" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Block user by ID</legend>
				<label>ID*</label>
				<input type="text" name="id" placeholder="Type ID" value="4">
				<span class="help-block">E.g. 7</span>
				<button type="submit" class="btn">Block</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/unblockuserbyemail" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Unblock user by email</legend>
				<label>Email*</label>
				<input type="text" name="email" placeholder="Type email" value="user@awrank.com">
				<span class="help-block">E.g. okorokhina@gmail.com</span>
				<button type="submit" class="btn">Unblock</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>

	<div class="well">
		<form method="POST" action="<c:url value="admin/unblockuserbyid" />" accept="application/json"
			  enctype="application/json">
			<fieldset>
				<legend>Unblock user by ID</legend>
				<label>ID*</label>
				<input type="text" name="id" placeholder="Type ID" value="4">
				<span class="help-block">E.g. 7</span>
				<button type="submit" class="btn">Unlock</button>
				<button type="reset" class="btn">Reset</button>
			</fieldset>
		</form>
	</div>
</div>

<div class="tab-pane" id="tabKnownIssues">
	<div class="well">
		<ul>
			<li>Values for columns <code>createBy</code> and <code>lastModifiedBy</code> are not stored.</li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
</div>

</div>
</div>

<footer>
	<p>&copy; AwRank 2013</p>
</footer>

</div>

</body>
</html>