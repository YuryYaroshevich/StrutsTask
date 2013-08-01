<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/struts-nested.tld" prefix="nested"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<bean:define id="categoryName" name="shopForm" property="categoryName" />
<title>Subcategories of ${categoryName}</title>
</head>
<body>
	<h2>Subcategories of ${categoryName}</h2>

	<ul>
		<bean:define id="category" name="shopForm"
			property="productsJDOM.rootElement.children[${shopForm.categoryId}]" />

		<nested:iterate name="category" property="children" id="subcategory">
			<nested:define id="subcategName" property="attribute(name).value" />

			<li><a
				href="shop.do?method=goods&categoryName=${categoryName}
									&subcategoryName=${subcategName}">
					${subcategName}</a></li>
		</nested:iterate>
	</ul>

	<a href="shop.do?method=categories">Back</a>
</body>
</html>