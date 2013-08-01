package com.epam.st.util;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.epam.st.product.Product;

public final class ProductsJDOMHandler {
	private static String NAME_ATTR = "name";

	private ProductsJDOMHandler() {
	}

	public static int getCategoryListIndex(String categName,
			Document productsJDOM) {
		Element root = productsJDOM.getRootElement();
		List<Element> categories = root.getChildren();
		String name = null;
		for (Element category : categories) {
			name = category.getAttributeValue(NAME_ATTR);
			if (categName.equals(name)) {
				return categories.indexOf(category);
			}
		}
		return -1;
	}

	public static int getSubcategoryListIndex(int categId, String subcategName,
			Document productsJDOM) {
		Element root = productsJDOM.getRootElement();
		Element category = root.getChildren().get(categId);
		List<Element> subcategories = category.getChildren();
		String name = null;
		for (Element subcateg : subcategories) {
			name = subcateg.getAttributeValue(NAME_ATTR);
			if (subcategName.equals(name)) {
				return subcategories.indexOf(subcateg);
			}
		}
		return -1;
	}
	
	public static Product processProductsJDOM(Document productsJDOM) {
		
		return null;
	}
	
	

	

	public static int getGoodsNumbInCategory(String categName,
			Document productsJDOM) {
		int categId = getCategoryListIndex(categName, productsJDOM);
		if (categId == -1) {
			return 0;
		}
		int goodsNumber = 0;
		Element correspondCateg = productsJDOM.getRootElement().getChildren()
				.get(categId);
		List<Element> subcategories = correspondCateg.getChildren();
		for (Element subcateg : subcategories) {
			goodsNumber += getGoodsNumbInSubcateg(categId,
					subcateg.getAttributeValue(NAME_ATTR), productsJDOM);
		}
		return goodsNumber;
	}

	public static int getGoodsNumbInSubcateg(int categId, String subcategName,
			Document productsJDOM) {
		int subcategId = getSubcategoryListIndex(categId, subcategName,
				productsJDOM);
		if (subcategId == -1) {
			return 0;
		}
		Element correspondCateg = productsJDOM.getRootElement().getChildren()
				.get(categId);
		Element correspondSubcateg = correspondCateg.getChildren().get(subcategId);
		return correspondSubcateg.getChildren().size();
	}
}
