<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add good</title>
</head>
<body>
	<bean:define id="categName" name="shopForm" property="categoryName" />
	<bean:define id="subcategName" name="shopForm"
		property="subcategoryName" />

	<h2>Add good in ${subcategName} subcategory</h2>

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
				<td>Price:</td>
				<td><html:text name="shopForm" property="price" /></td>
			</tr>
			<tr>
				<td>Not in stock:</td>
				<td><html:checkbox name="shopForm" property="notInStock"
						value="true" /></td>
			</tr>
		</table>
		<html:submit>ADD GOOD</html:submit>
	</html:form>

</body>
</html>