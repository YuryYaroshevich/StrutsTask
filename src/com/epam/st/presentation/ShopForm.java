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
	
	//good properties
	private String producer;
	private String model;
	private String dateOfIssue;
	private String color;
	private String price;
	private boolean notInStock; 

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

	public boolean isGoodNotInStock() {
		return notInStock;
	}

	public void setNotInStock(String notInStock) {
		if (notInStock != null) {
			this.notInStock = true;
		} else {
			this.notInStock = false;
		}
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
