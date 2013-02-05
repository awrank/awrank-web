<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" % -->
<!-- %@ taglib prefix="form" uri="http://www.springframework.org/tags/form"% -->
<%@ page session="true" %>
<html>
<head>
    <title>Sending email test page </title>
    <link href="/WEB-INF/css/main.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<h2> Mail test via Jango SMTP: </h2>

<form name="frm1" method="post" action="sendtestjungosmtp">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="22%">&nbsp;</td>
    <td width="78%">&nbsp;</td>
    </tr>
  <tr>
    <td> <b>SMTP host:</b>  <br/> </td>
    <td><input type="text" name="jsmpt_host_name" value=${jsmpt_host_name}></td>
  </tr>
  <tr>
    <td> <b>SMTP port:</b>  <br/> </td>
    <td><input type="text" name="jsmpt_port" value=${jsmpt_port}></td>
  </tr>
  <tr>
    <td> <b>SMTP user name:</b>  <br/> </td>
    <td><input type="text" name="jsmpt_user_name" value=${jsmpt_user_name}></td>
  </tr>
  <tr>
    <td> <b>SMTP password:</b> <br/> </td>
    <td><input type="text" name="jsmpt_password" value=${jsmpt_password}></td>
  </tr>
  <tr>
    <td> <b>email from (aw):</b><br/> </td>
    <td><input type="text" name="smpt_from_email" value=${smpt_from_email}></td>
  </tr>
  <tr>
    <td> <b>email to authenticate:</b> <br/> </td>
    <td><input type="text" name="testactivation_email" value=${testactivation_email}></td>
  </tr>
   <tr>
    <td> <b>password to authenticate:</b> <br/> </td>
    <td><input type="text" name="testactivation_password" value=${testactivation_password}></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="submit" value="Send test activation email via Jango SMTP"></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
</table>
</form>

<!-- p>
    <b>SMTP host:</b> ${jsmpt_host_name} <br/>
    <b>SMTP port:</b> ${jsmpt_port} <br/>
    <b>SMTP username:</b> ${jsmpt_user_name} <br/>
    <b>SMTP password:</b> ${jsmpt_password}<br/>
    <br/>
    <b>Mail from address:</b> ${smpt_from_email}<br/>
    
    <b>Mail test activation email:</b> ${testactivation_email}<br/>
    <b>Mail test activation password:</b> ${testactivation_password}<br/>
</p>
<p>
    <a id="testSMTP" href=sendtestjungosmtp>Send test activation email via Jango SMTP</a>
</p -->

<hr />

<h2> Mail test via Send Grid: </h2>


<form name="frm1" method="post" action="sendtestsendgrid">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="22%">&nbsp;</td>
    <td width="78%">&nbsp;</td>
    </tr>
  <tr>
    <td> <b>SMTP host:</b>  <br/> </td>
    <td><input type="text" name="sgsmpt_host_name" value=${sgsmpt_host_name}></td>
  </tr>
  <tr>
    <td> <b>SMTP port:</b>  <br/> </td>
    <td><input type="text" name="sgsmpt_port" value=${sgsmpt_port}></td>
  </tr>
  <tr>
    <td> <b>SMTP user name:</b>  <br/> </td>
    <td><input type="text" name="sgsmpt_user_name" value=${sgsmpt_user_name}></td>
  </tr>
  <tr>
    <td> <b>SMTP password:</b> <br/> </td>
    <td><input type="text" name="sgsmpt_password" value=${sgsmpt_password}></td>
  </tr>
  <tr>
    <td> <b>email from (aw):</b><br/> </td>
    <td><input type="text" name="smpt_from_email" value=${smpt_from_email}></td>
  </tr>
  <tr>
    <td> <b>email to authenticate:</b> <br/> </td>
    <td><input type="text" name="testactivation_email" value=${testactivation_email}></td>
  </tr>
   <tr>
    <td> <b>password to authenticate:</b> <br/> </td>
    <td><input type="text" name="testactivation_password" value=${testactivation_password}></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="submit" value="Send test activation email via SendGrid"></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
</table>
</form>
<!-- p>
    <b>SMTP host:</b> ${sgsmpt_host_name} <br/>
    <b>SMTP port:</b> ${sgsmpt_port} <br/>
    <b>Mail username:</b> ${sgsmpt_user_name} <br/>
    <b>Mail password:</b> ${sgsmpt_password}<br/>
    <b>Mail from address:</b> ${smpt_from_email}<br/>
    
    <b>Mail test activation email:</b> ${testactivation_email}<br/>
    <b>Mail test activation password:</b> ${testactivation_password}<br/>
</p>
<p>
    <a id="testSMTP" href="sendtestsendgrid">Send test activation email via Send Grid</a>
</p -->

</body>
</html>
