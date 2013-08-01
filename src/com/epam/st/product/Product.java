package com.epam.st.product;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private List<Category> categories;
	
	public Product() {
		categories = new ArrayList<Category>();
	}
	
	public void addCategory(Category categ) {
		categories.add(categ);
	}
}
