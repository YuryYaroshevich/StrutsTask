<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/struts-nested.tld" prefix="nested"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Categories</title>
</head>
<body>
	<h2>Categories</h2>
	<nested:define id="root" name="shopForm"
		property="productsJDOM.rootElement" scope="session" />
	<ul>
		<nested:iterate name="root" property="children" id="category">
			<bean:define id="attribute" name="category" property="attributes[0]" />
			<li><a
				href="shop.do?method=subcategories&categName=${attribute.value}">
					<nested:write name="attribute" property="value" />
			</a></li>
		</nested:iterate>
	</ul>
</body>
</html>