package com.epam.st.presentation;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

public final class ShopForm extends ActionForm {
	private static final long serialVersionUID = 421564645392100602L;

	private Document productsJDOM;
	
	public Document getProductsJDOM() {
		return productsJDOM;
	}
	
	public void setProductsJDOM(Document productsJDOM) {
		this.productsJDOM = productsJDOM;
	}
}
