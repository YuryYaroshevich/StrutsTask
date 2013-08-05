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

	<nested:form action="shop.do?method=updateGoods">
		<table>
			<tr>
				<td>Producer</td>
				<td>Model</td>
				<td>Date of issue</td>
				<td>Color</td>
				<td>Price</td>
			</tr>
			<logic:iterate name="shopForm"
				property="productsJDOM.rootElement.children[${categoryId}].children[${subcategoryId}].children"
				id="good" indexId="goodId">

				<nested:nest
					property="productsJDOM.rootElement.children[${categoryId}].children[${subcategoryId}].children[${goodId}]">
					<tr>
						<td><nested:text property="children[0].text" /></td>
						<td><nested:text property="children[1].text" /></td>
						<td><nested:text property="children[2].text" /></td>
						<td><nested:text property="children[3].text" /></td>
						<logic:equal name="good" value="price" property="children[4].name">
							<td><nested:text property="children[4].text" /></td>
						</logic:equal>
						<logic:notEqual name="good" value="price"
							property="children[4].name">
							<td><nested:text property="children[4].text"
									value="not in stock" /></td>
						</logic:notEqual>
					</tr>
				</nested:nest>
				<br></br>
			</logic:iterate>
		</table>
		<nested:submit>UPDATE GOODS</nested:submit>
	</nested:form>
	<br></br>

	<html:form action="shop.do?method=addGood" method="POST">
		<html:hidden property="categoryName" value="${categoryName}" />
		<html:hidden property="subcategoryName" value="${subcategoryName}" />
		<html:submit>ADD GOOD</html:submit>
	</html:form>
	<br></br>
	<html:form action="shop.do?method=categories">
		<html:submit>BACK</html:submit>
	</html:form>
</body>
</html>