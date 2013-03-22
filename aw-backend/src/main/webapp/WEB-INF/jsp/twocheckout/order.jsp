<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Payment</title>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8">
</head>
<body>

<form action="https://www.2checkout.com/checkout/spurchase" method="post">
	<input type="hidden" name="sid" value="${sid}"/>
	<input type="hidden" name="mode" value="2CO"/>
	<input type="hidden" name="merchant_order_id" value="${merchant_order_id}"/>
	<input type="hidden" name="li_0_name" value="${li_0_name}"/>
	<input type="hidden" name="li_0_price" value="${li_0_price}"/>
	<c:if test="${testMode}">
		<input type="hidden" name="demo" value="Y"/>
	</c:if>
</form>
<script type="text/javascript">document.forms[0].submit();</script>

</body>
</html>