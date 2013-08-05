package com.epam.st.constant;

public final class STConstant {
	// key for getting products.xml path from property file
	public static final String PRODUCTS_XML = "products.xml";

	// key for getting saveGood.xslt path from property file
	public static final String SAVE_GOOD_XSLT = "save.good.xslt";

	// keys for error messages
	public static final String EMPTY_DATE = "empty.date";
	public static final String WRONG_DATE_RANGE = "wrong.date.range";
	public static final String WRONG_DATE_FORMAT = "wrong.date.format";
	public static final String WRONG_PRICE_FORMAT = "wrong.price.format";

	// keys for XML info
	public static final String NAMESPACE_URI = "namespace.uri";
	public static final String NAMESPACE_PREFIX = "namespace.prefix";

	// I use these constants for retrieving values from request and setting
	// parameters in transformer
	public static final String CATEGORY_NAME = "categoryName";
	public static final String SUBCATEGORY_NAME = "subcategoryName";

	private STConstant() {
	}
}
