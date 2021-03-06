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
<link rel="stylesheet" type="text/css" href="css/error.css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="css/goods.css"
	media="screen" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/validation.js"></script>
<script type="text/javascript">
	var emptyProducerMsg = '<bean:message key="empty.producer" />';
	var emptyModelMsg = '<bean:message key="empty.model" />';
	var wrongModelFormatMsg = '<bean:message key="wrong.model.format" />';
	var emptyDateMsg = '<bean:message key="empty.date" />';
	var wrongDateFormatMsg = '<bean:message key="wrong.date.format" />';
	var wrongDateRangeMsg = '<bean:message key="wrong.date.range" />';
	var emptyColorMsg = '<bean:message key="empty.color" />';
	var emptyPriceMsg = '<bean:message key="empty.price" />';
	var wrongPriceFormatMsg = '<bean:message key="wrong.price.format" />';
</script>
<bean:define id="subcategoryName" name="shopForm"
	property="subcategoryName" />
<title>Goods of ${subcategoryName}</title>
</head>
<body>
	<h2>Goods of ${subcategoryName}</h2>

	<bean:define id="categoryName" name="shopForm" property="categoryName" />
	<bean:define id="categoryId" name="shopForm" property="categoryId" />
	<bean:define id="subcategoryId" name="shopForm"
		property="subcategoryId" />

	<div id="content">
		<nested:form action="shop.do?method=updateGoods"
			styleId="update-goods">
			<table>
				<tr>
					<td>Producer</td>
					<td>Model</td>
					<td>Date of issue</td>
					<td>Color</td>
					<td>Price</td>
				</tr>
				<nested:nest
					property="productsJDOM.rootElement.children[${categoryId}].children[${subcategoryId}]">
					<nested:iterate property="children" id="good" indexId="goodId">
						<tr id="${goodId}" class="good-parameters">
							<td><nested:text size="20" styleClass="producer"
									property="children[0].text" /></td>
							<td><nested:text size="20" styleClass="model"
									property="children[1].text" /></td>
							<td><nested:text size="20" styleClass="date-of-issue"
									property="children[2].text" /></td>
							<td><nested:text size="20" styleClass="color"
									property="children[3].text" /></td>
							<logic:equal name="good" value="price"
								property="children[4].name">
								<td><nested:text size="20" styleClass="price"
										property="children[4].text" /></td>
							</logic:equal>
							<nested:notEqual value="price"
								property="children[4].name">
								<td><nested:text size="20" styleClass="price"
										property="children[4].text" value="not in stock" /></td>
							</nested:notEqual>
						</tr>
					</nested:iterate>
				</nested:nest>
			</table>
			<nested:submit styleId="update-button">UPDATE GOODS</nested:submit>
		</nested:form>

		<div id="control-buttons">
			<html:form styleId="back-button"
				action="shop.do?method=subcategories&categoryName=${categoryName}">
				<html:submit>BACK</html:submit>
			</html:form>
			<html:form action="shop.do?method=addGood" method="POST"
				styleId="add-button">
				<html:hidden property="categoryName" value="${categoryName}" />
				<html:hidden property="subcategoryName" value="${subcategoryName}" />
				<html:submit>ADD</html:submit>
			</html:form>
		</div>
	</div>


</body>
</html>