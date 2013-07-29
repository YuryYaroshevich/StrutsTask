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
	
	<nested:define id="root" name="shopForm"
		property="productsJDOM.rootElement" scope="session" />

	<ul>
		<nested:iterate name="root" property="children" id="category">
			<nested:equal name="category" property="attributes[0].value"
				value="${categoryName}">

				<nested:iterate name="category" property="children" id="subcategory">
					<bean:define id="subcategName" name="subcategory"
						property="attributes[0].value" />
					<li><a
						href="shop.do?method=goods&categoryName=${categoryName}
									&subcategoryName=${subcategName}">
							${subcategName}</a></li>
				</nested:iterate>
				
			</nested:equal>
		</nested:iterate>
	</ul>
	
	<a href="shop.do?method=categories">Back</a>
</body>
</html>