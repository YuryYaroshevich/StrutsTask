<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/addGoodForm.css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="css/error.css"
	media="screen" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/cancel.js"></script>
<title>Add good</title>
</head>
<body>
	<bean:define id="categName" name="shopForm" property="categoryName" />
	<bean:define id="subcategName" name="shopForm"
		property="subcategoryName" />

	<h2>Add good in ${subcategName} subcategory</h2>

	<html:errors />

	<html:form action="shop.do?method=saveGood" method="POST">
		<table>
			<tr>
				<td>Producer:</td>
				<td><html:text name="shopForm" property="producer" /></td>
			</tr>
			<tr>
				<td>Model(LLNNN, L - letter, N - number):</td>
				<td><html:text name="shopForm" property="model" /></td>
			</tr>
			<tr>
				<td>Date of issue(dd-MM-YYYY):</td>
				<td><html:text name="shopForm" property="dateOfIssue" /></td>
			</tr>
			<tr>
				<td>Color:</td>
				<td><html:text name="shopForm" property="color" /></td>
			</tr>
			<tr>
				<td>Price(number or "not in stock"):</td>
				<td><html:text name="shopForm" property="price" /></td>
			</tr>
		</table>
		<div id="control-buttons">
			<html:submit styleId="save">SAVE GOOD</html:submit>
			<html:cancel styleId="cancel">CANCEL</html:cancel>
		</div>
	</html:form>
</body>
</html>