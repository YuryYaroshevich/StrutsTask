package com.epam.st.stconstant;

public final class STConstant {
	// key for getting products.xml path from property file	
	public static final String PRODUCTS_XML = "products.xml";
	
	// key for getting saveGood.xslt path from property file
	public static final String SAVE_GOOD_XSLT = "save.good.xslt";
	
	// keys for error messages
	public static final String EMPTY_DATE = "empty.date";
	public static final String WRONG_DATE_RANGE = "wrong.date.range";
	public static final String WRONG_DATE_FORMAT = "wrong.date.format";
	public static final String NOT_IN_STOCK_TO_TRUE = "not.in.stock.to.true";
	public static final String NOT_IN_STOCK_TO_FALSE = "not.in.stock.to.false";
	public static final String WRONG_PRICE_FORMAT = "wrong.price.format";
	
	private STConstant() {
	}
}
