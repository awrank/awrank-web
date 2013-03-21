<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Awrank : Email verification response</title>
	
	<link rel="shortcut icon" href="resources/img/favicon.ico">

	<!-- CSS styles -->
	<link href="../../../resources/css/bootstrap.css" rel="stylesheet">
	<link href="../../../resources/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="../../../resources/css/bootstrap-formhelpers-countries.flags.css" rel="stylesheet">
	<link href="../../../resources/css/awrank.css" rel="stylesheet">

	<style type="text/css">
		body {
			padding-top: 60px;
			padding-bottom: 40px;
		}

		.sidebar-nav {
			padding: 9px 0;
		}
	</style>

	<!-- JS -->
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.js"></script>
	<!--<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>-->
	<script type="text/javascript" src="http://jquery-json.googlecode.com/files/jquery.json-2.4.js"></script>
	<!--<script type="text/plain" src="http://jquery-json.googlecode.com/files/jquery.json-2.4.min.js"></script>-->
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-transition.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-alert.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-modal.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-dropdown.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-scrollspy.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-tab.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-tooltip.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-popover.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-button.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-collapse.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-carousel.js"></script>
	<script type="text/javascript" src="../../../resources/js/bootstrap/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="../../../resources/js/underscore.js"></script>
	<script type="text/javascript" src="../../../resources/js/backbone.js"></script>
	<script type="text/javascript" src="../../../resources/js/awrank.js"></script>
	<script type="text/javascript" src="../../../resources/js/awrank_lng.js"></script>
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

</head>


<body onload="fIndexBodyOnload()">

<c:set var="context" value="<%=request.getContextPath()%>"/>

<div class="container-fluid">

	<%-- Top navigation bar --%>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="#">AwRank</a>
				<ul class="nav pull-right" >
					<li class="dropdown" style="z-index: 10000">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-globe icon-white"></i>
							<span id="languageLabel">RU</span> <b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#" onclick="$('#languageLabel').text('EN');setLanguage('EN')"><i
									class="icon-flag-GB"></i>EN</a></li>
							<li><a href="#" onclick="$('#languageLabel').text('RU');setLanguage('RU')"><i
									class="icon-flag-RU"></i>RU</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span2">
			<div class="well sidebar-nav">
				<ul id="menu" class="nav nav-list">
					<li class="nav-header" lngt="SITE">SITE</li>
					<li id="menuItemHome">
						<a href="${context}/index.html">
							<i class="icon-home"></i><span lngt="HOME">Home</span>
						</a>
					</li>
					<li id="menuItemIndexJsp">
						<a href="${context}">
							<i class="icon-shopping-cart"></i><span>index.jsp</span>
						</a>
					</li>

				</ul>
			</div>
		</div>

		<div class="span10">
			<div name="alert-error"></div>
			<div name="alert-warning"></div>
			<div name="alert-success"></div>
			<c:choose>
				<c:when test="${responseMap['result'] eq 'failure'}">
					<h2 class="text-error"><span lngt="${responseMap['reason']}"></span></h2>
					<h4>
						<span lngt="DESCRIPTION"></span>
						<p class="text-info">
							<span lngt="${responseMap['reason']}"></span>
						</p>
						<br/>
					</h4>
				</c:when>
				<c:otherwise>
					<h2>
						<p class="text-success">
							<span lngt="${responseMap['reason']}"></span>
						</p>
					</h2>
					<span lngt="FORGOT_FORM_INSTRUCTIONS"></span>
						<span lngt="DESCRIPTION"></span>
						<p class="text-info">
							<span lngt="${responseMap['reason']}"></span>
						</p>
					<input type="hidden" id="forgot_localIP" class="span11"  value="${localIP}" />
					<input type="hidden" id="forgot_remoteIP" class="span11"  value="${remoteIP}" />
				    <input type="hidden" id="forgot_entrypointid" class="span11" value="${entryPointId}"/>
					<div class="input-prepend">
                        <span class="add-on"><i class="icon-lock"></i></span>
                        <input type="password" id="forgot_newpass" class="span11" lngp="PROFILE_FIELD_NEW_PASSWORD" />
                    </div>
					<div class="input-prepend">
                        <span class="add-on"><i class="icon-lock"></i></span>
                        <input type="password" id="forgot_newpass_confirm" class="span11" lngp="PROFILE_FIELD_NEW_PASSWORD_CONFIRM" />
                    </div>
 					 <div class="row-fluid"> 
                        <div class="span12">
                            	<button class="btn btn-large btn-primary pull-right"       
                                    lngt="SUBMIT" onClick="fChangePasswordSubmit();"></button>
                        </div>
 					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<footer>
		<p >&copy; AwRank 2013</p>
	</footer>

</div>

<script type="text/javascript" language="JavaScript">

	function fIndexBodyOnload() {
		
		setContextPath("../../../");
		setLanguage('EN');
	}

	function fChangePasswordSubmit() {
		 
		  var entrypointid = $('#forgot_entrypointid').val();
		  var newpassword = $('#forgot_newpass').val();
	      var newconfirmation = $('#forgot_newpass_confirm').val();
	      var localIP = $('#forgot_localIP').val();
		  var remoteIP = $('#forgot_remoteIP').val();
	         
		 fIndexClearMessages("divProfile");
		 var iserrors = false;
		 
		 if(entrypointid == null){
			 alertError(getMessage('ERROR'), getMessage('ERROR_ACCESS'));
			 iserrors = true;
		 }
		 
		 if(newpassword.length < 3 || newconfirmation.length < 3 || newpassword != newconfirmation){
      	 	alertError(getMessage('ERROR'), getMessage('REGISTRATION_PASSWORD_AND_CONFIRMATION_ARE_NOT_EQUAL'));
      	 	iserrors = true;
       }
		 
		 if(iserrors == false) send_user_new_password_forgot({entrypointid: entrypointid, newpassword : newpassword, newconfirmation : newconfirmation, localIP: localIP, remoteIP: remoteIP});
	}
</script>

</body>
</html>