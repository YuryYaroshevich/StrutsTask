package com.epam.st.presentation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;
import org.jdom2.Document;

import com.util.GoodValidator;

public final class ShopForm extends ValidatorForm {
	private static final long serialVersionUID = 421564645392100602L;

	// contains JDOM representation of products.xml
	private Document productsJDOM;

	// contains names of category and subcategory for page titles
	private String categoryName;
	private String subcategoryName;

	// good properties
	private String producer;
	private String model;
	private String dateOfIssue;
	private String color;
	private String price;
	private String notInStock;

	public void reset(ActionMapping mapping, HttpServletRequest req) {
		producer = null;
		model = null;
		dateOfIssue = null;
		color = null;
		price = null;
		notInStock = null;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
		ActionErrors errors = super.validate(mapping, req);
		if (errors == null) {
			errors = new ActionErrors();
		}
		ActionMessages errorMessages = new ActionMessages();
		// validate date
		String errorMessageKey = GoodValidator.validateDate(dateOfIssue);
		processErrorMessageKey(errors, errorMessageKey);
		// validate shop state of good
		errorMessageKey = GoodValidator.validateShopState(price, notInStock);
		processErrorMessageKey(errors, errorMessageKey);
		if (!errorMessages.isEmpty()) {
			errors.add(errorMessages);
		}
		return errors;
	}

	private static void processErrorMessageKey(ActionMessages errors, String key) {
		if (!GoodValidator.VALID.equals(key)) {
			errors.add(Globals.ERROR_KEY, new ActionMessage(key));
		}
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNotInStock() {
		return notInStock;
	}

	public void setNotInStock(String notInStock) {
		this.notInStock = notInStock;
	}

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
