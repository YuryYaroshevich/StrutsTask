<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-html.tld" prefix="html"%>
<%@ taglib uri="/struts-nested.tld" prefix="nested"%>
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

	<nested:define id="root" name="shopForm"
		property="productsJDOM.rootElement" />

	<nested:define id="categoryName" name="shopForm"
		property="categoryName" />

	<nested:iterate id="category" name="root" property="children">
		<nested:equal name="category" property="attributes[0].value"
			value="${categoryName}">

			<nested:iterate id="subcategory" name="category" property="children">
				<nested:equal name="subcategory" property="attributes[0].value"
					value="${subcategoryName}">

					<nested:iterate id="good" name="subcategory" property="children">
						<table>
							<nested:iterate id="goodFeature" name="good" property="children">
								<tr>
									<td>${goodFeature.name}</td>
									<td>${goodFeature.value}</td>
								</tr>
							</nested:iterate>
						</table>
						<br></br>
					</nested:iterate>

				</nested:equal>
			</nested:iterate>

		</nested:equal>
	</nested:iterate>

	<html:form action="shop.do?method=addGood" method="POST">
		<input type="hidden" name="categoryName" value="${categoryName}" />
		<input type="hidden" name="subcategoryName" value="${subcategoryName}" />
		<input type="submit" value="ADD GOOD" />
	</html:form>

	<a href="shop.do?method=subcategories&categoryName=${categoryName}">Back</a>
</body>
</html>