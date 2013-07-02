package com.epam.st.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category {
	private String name;

	private List<Subcategory> subcategories;

	public Category(String name) {
		this.name = name;
		subcategories = new ArrayList<Subcategory>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subcategory> getSubcategories() {
		return Collections.unmodifiableList(subcategories);
	}

	public void addSubcategory(Subcategory subcategory) {
		subcategories.add(subcategory);
	}

	@Override
	public String toString() {
		return "Category [name=" + name + ", subcategories=" + subcategories
				+ "]";
	}
}
