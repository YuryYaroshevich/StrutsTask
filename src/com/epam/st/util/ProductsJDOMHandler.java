package com.epam.st.util;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import static com.epam.st.stconstant.STConstant.*;
import static com.resource.PropertyGetter.getProperty;

public final class ProductsJDOMHandler {
	private static final String NAME_ATTR = "name";

	private static final String PRICE_ELEM = "price";
	private static final String NOT_IN_STOCK_ELEM = "not-in-stock";

	private static final String PREFIX = "pr";

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

	public static int countGoodsInCateg(String categName, Document productsJDOM) {
		int categId = getCategoryListIndex(categName, productsJDOM);
		if (categId == -1) {
			return 0;
		}
		int goodsNumber = 0;
		Element correspondCateg = productsJDOM.getRootElement().getChildren()
				.get(categId);
		List<Element> subcategories = correspondCateg.getChildren();
		for (Element subcateg : subcategories) {
			goodsNumber += countGoodsInSubcateg(categId,
					subcateg.getAttributeValue(NAME_ATTR), productsJDOM);
		}
		return goodsNumber;
	}

	public static int countGoodsInSubcateg(int categId, String subcategName,
			Document productsJDOM) {
		int subcategId = getSubcategoryListIndex(categId, subcategName,
				productsJDOM);
		if (subcategId == -1) {
			return 0;
		}
		Element correspondCateg = productsJDOM.getRootElement().getChildren()
				.get(categId);
		Element correspondSubcateg = correspondCateg.getChildren().get(
				subcategId);
		return correspondSubcateg.getChildren().size();
	}

	// Append not-in-stock element to the goods which has got empty price
	// element
	public static void setCorrespondShopState(Document productsJDOM,
			int categId, int subcategId) {
		Element priceElem = null;
		Element notInStockElem = null;
		Namespace namesp = Namespace.getNamespace(PREFIX,
				getProperty(NAMESPACE_URI));
		// fetch correspond category
		Element category = productsJDOM.getRootElement().getChildren()
				.get(categId);
		// fetch correspond subcategory
		Element subcategory = category.getChildren().get(subcategId);
		List<Element> goods = subcategory.getChildren();
		for (Element good : goods) {
			priceElem = good.getChild(PRICE_ELEM, namesp);
			if (priceElem == null) {
				notInStockElem = good.getChild(NOT_IN_STOCK_ELEM, namesp);
				if (!notInStockElem.getText().isEmpty()) {
					notInStockElem.setName(PRICE_ELEM);
				}
			} else if (priceElem.getText().isEmpty()) {
				good.removeChild(PRICE_ELEM, namesp);
				good.addContent(notInStockElem);
			}
		}
	}
}
