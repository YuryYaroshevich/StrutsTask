<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<nested:define id="subcategoryName" name="shopForm"
	property="subcategoryName" />
<title>Goods of ${subcategoryName}</title>
</head>
<body>
	<h2>Goods of ${subcategoryName}</h2>

	<nested:define id="categoryName" name="shopForm"
		property="categoryName" />

	<bean:define id="categoryId" name="shopForm" property="categoryId" />
	<bean:define id="subcategoryId" name="shopForm"
		property="subcategoryId" />

	<nested:iterate name="shopForm"
		property="productsJDOM.rootElement.children[${categoryId}].children[${subcategoryId}].children"
		id="good">
		<nested:write property="child(price).text" />
		<nested:form action="shop.do?method=updateGoods">
			<table>
				<tr>
					<td>Producer:</td>
					<td><nested:text name="good" property="children[0].text" /></td>
				</tr>
				<tr>
					<td>Model:</td>
					<td><nested:text name="good" property="children[1].text" /></td>
				</tr>
				<tr>
					<td>Date of issue:</td>
					<td><nested:text name="good" property="children[2].text" /></td>
				</tr>
				<tr>
					<td>Color:</td>
					<td><nested:text name="good" property="children[3].text" /></td>
				</tr>

				<logic:present name="good" property="child(price)">
					<tr>
						<td>Price:</td>
						<td><nested:text name="good" property="children[4].text" /></td>
					</tr>
				</logic:present>
				<logic:present name="good" property="child(notInStock)">
					<tr>
						<td>Not in stock</td>
					</tr>
				</logic:present>
			</table>
			<nested:submit>UPDATE GOODS</nested:submit>
		</nested:form>
		<br></br>
	</nested:iterate>

	<html:form action="shop.do?method=addGood" method="POST">
		<html:hidden property="categoryName" value="${categoryName}" />
		<html:hidden property="subcategoryName" value="${subcategoryName}" />
		<html:submit>ADD GOOD</html:submit>
	</html:form>

	<a href="shop.do?method=subcategories&categoryName=${categoryName}">Back</a>
</body>
</html>