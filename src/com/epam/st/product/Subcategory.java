package com.epam.st.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subcategory {
	private String name;
	
	private List<Good> goods;
	
	public Subcategory(String name) {
		this.name = name;
		goods = new ArrayList<Good>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Good> getGoods() {
		return Collections.unmodifiableList(goods);
	}
	
	public void addGood(Good good) {
		goods.add(good);
	}

	@Override
	public String toString() {
		return "Subcategory [name=" + name + ", goods=" + goods + "]";
	}
}
