package com.epam.st.presentation;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

public final class ShopForm extends ActionForm {
	private static final long serialVersionUID = 421564645392100602L;

	// contains JDOM representation of products.xml
	private Document productsJDOM;

	// contains names of category and subcategory for page titles
	private String categoryName;
	private String subcategoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}

	public Document getProductsJDOM() {
		return productsJDOM;
	}

	public void setProductsJDOM(Document productsJDOM) {
		this.productsJDOM = productsJDOM;
	}
}
