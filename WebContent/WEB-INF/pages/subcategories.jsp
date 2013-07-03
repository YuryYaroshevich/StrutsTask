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
	<bean:define id="productsJDOM" name="shopForm" property="productsJDOM"
		scope="session" />
	<bean:define id="root" name="productsJDOM" property="rootElement" />

	<ul>
		<nested:iterate name="root" property="children" id="category">

			<nested:iterate name="category" property="attributes" id="categAttr">

				<nested:equal name="categAttr" property="value"
					value="${shopForm.categoryName}">

					<nested:iterate name="category" property="children"
						id="subcategory">

						<nested:iterate name="subcategory" property="attributes"
							id="subcategAttr">

							<nested:equal name="subcategAttr" property="name" value="name">
								<li><a
									href="shop.do?method=goods&categName=${categoryName}
									&subcategName=${subcategAttr.value}">
									${subcategAttr.value}</a></li>
							</nested:equal>
						</nested:iterate>
					</nested:iterate>
				</nested:equal>
			</nested:iterate>
		</nested:iterate>
	</ul>
</body>
</html>