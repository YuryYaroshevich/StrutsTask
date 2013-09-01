package com.epam.st.presentation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.jdom2.Document;

import com.epam.st.model.Good;

public final class ShopForm extends ValidatorForm {
	private static final long serialVersionUID = 421564645392100602L;

	// contains JDOM representation of products.xml
	private Document productsJDOM;

	// contains names of category and subcategory for page titles
	private String categoryName;
	private String subcategoryName;

	// ids
	private int categoryId;
	private int subcategoryId;

	private Good good;

	public ShopForm() {
		good = new Good();
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest req) {
		good.setColor(null);
		good.setDateOfIssue(null);
		good.setModel(null);
		good.setPrice(null);
		good.setProducer(null);
	}

	public Good getGood() {
		return good;
	}

	public String getProducer() {
		return good.getProducer();
	}

	public void setProducer(String producer) {
		good.setProducer(producer);
	}

	public String getModel() {
		return good.getModel();
	}

	public void setModel(String model) {
		good.setModel(model);
	}

	public String getDateOfIssue() {
		return good.getDateOfIssue();
	}

	public void setDateOfIssue(String dateOfIssue) {
		good.setDateOfIssue(dateOfIssue);
	}

	public String getColor() {
		return good.getColor();
	}

	public void setColor(String color) {
		good.setColor(color);
	}

	public String getPrice() {
		return good.getPrice();
	}

	public void setPrice(String price) {
		good.setPrice(price);
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
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
}
