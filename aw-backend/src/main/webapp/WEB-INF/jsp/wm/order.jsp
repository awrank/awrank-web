<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Payment</title>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8">
</head>
<body>

<form action="${webMoneyUrl}" method="post">
	<input type="hidden" name="LMI_PAYMENT_AMOUNT" value="${LMI_PAYMENT_AMOUNT}"/>
	<input type="hidden" name="LMI_PAYMENT_DESC_BASE64" value="${LMI_PAYMENT_DESC_BASE64}"/>
	<input type="hidden" name="LMI_PAYMENT_NO" value="${LMI_PAYMENT_NO}"/>
	<input type="hidden" name="LMI_PAYEE_PURSE" value="${LMI_PAYEE_PURSE}"/>
	<c:if test="${testMode}">
		<input type="hidden" name="LMI_SIM_MODE" value="0">
	</c:if>
</form>
<script type="text/javascript">document.forms[0].submit();</script>

</body>
</html>