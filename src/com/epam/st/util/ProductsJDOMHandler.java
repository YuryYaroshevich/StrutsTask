package com.epam.st.util;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import static com.epam.st.constant.STConstant.*;
import static com.epam.st.resource.PropertyGetter.getProperty;

public final class ProductsJDOMHandler {
	private static final String NAME_ATTR = "name";

	private static final String PRICE_ELEM = "price";
	private static final String NOT_IN_STOCK_ELEM = "not-in-stock";

	private static final String NOT_IN_STOCK_VALUE = "not in stock";

	private ProductsJDOMHandler() {
	}

	public static int getCategoryListIndex(String categName,
			Document productsJDOM) throws Exception {
		Element root = productsJDOM.getRootElement();
		List<Element> categories = root.getChildren();
		String name = null;
		for (Element category : categories) {
			name = category.getAttributeValue(NAME_ATTR);
			if (categName.equals(name)) {
				return categories.indexOf(category);
			}
		}
		throw new Exception("there is no such category");
	}

	public static int getSubcategoryListIndex(int categId, String subcategName,
			Document productsJDOM) throws Exception {
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
		throw new Exception("there is no such subcategory");
	}

	public static int countGoodsInCateg(String categName, Document productsJDOM)
			throws Exception {
		int categId = getCategoryListIndex(categName, productsJDOM);
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
			Document productsJDOM) throws Exception {
		int subcategId = getSubcategoryListIndex(categId, subcategName,
				productsJDOM);
		Element correspondCateg = productsJDOM.getRootElement().getChildren()
				.get(categId);
		Element correspondSubcateg = correspondCateg.getChildren().get(
				subcategId);
		return correspondSubcateg.getChildren().size();
	}

	// change price element to not-in-stock element and vice versa if need
	public static void setCorrespondShopState(Document productsJDOM,
			int categId, int subcategId) {
		Element priceElem = null;
		Element notInStockElem = null;
		Namespace namesp = Namespace.getNamespace(
				getProperty(NAMESPACE_PREFIX), getProperty(NAMESPACE_URI));
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
				// if user entered price, then not-in-stock element becomes
				// price element
				if (NOT_IN_STOCK_VALUE.equals(notInStockElem.getText())) {
					notInStockElem.removeContent();
				} else {
					notInStockElem.setName(PRICE_ELEM);
				}
			} else if (NOT_IN_STOCK_VALUE.equals(priceElem.getText())) {
				// if user entered 'not in stock' in price field, then price
				// element becomes not-in-stock element
				priceElem.removeContent();
				priceElem.setName(NOT_IN_STOCK_ELEM);
			} else {
				notInStockElem = good.getChild(NOT_IN_STOCK_ELEM, namesp);
				
			}
		}
	}
}
