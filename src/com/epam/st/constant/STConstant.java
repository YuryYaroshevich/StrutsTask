package com.epam.st.constant;

public final class STConstant {
	// keys for getting XML and XSLT files path from property file
	public static final String PRODUCTS_XML = "products.xml";
	public static final String SAVE_GOOD_XSLT = "save.good.xslt";
	public static final String CATEGORIES_XSLT = "categories.xslt";
	public static final String SUBCATEGORIES_XSLT = "subcategories.xslt";
	public static final String GOODS_XSLT = "goods.xslt";
	public static final String ADD_GOOD_XSLT = "add.good.xslt";
	public static final String VALIDATION_XSLT = "validation.xslt";

	// keys for XML info
	public static final String NAMESPACE_URI = "namespace.uri";
	public static final String NAMESPACE_PREFIX = "namespace.prefix";

	// I use these constants for retrieving values from request and setting
	// parameters in transformer
	public static final String CATEGORY_NAME = "categoryName";
	public static final String SUBCATEGORY_NAME = "subcategoryName";

	// keys for error messages
	public static final String EMPTY_PRODUCER = "empty.producer";
	public static final String EMPTY_MODEL = "empty.model";
	public static final String WRONG_MODEL_FORMAT = "wrong.model.format";
	public static final String EMPTY_DATE = "empty.date";
	public static final String WRONG_DATE_FORMAT = "wrong.date.format";
	public static final String WRONG_DATE_RANGE = "wrong.date.range";
	public static final String EMPTY_COLOR = "empty.color";
	public static final String PRICE_IS_EMPTY = "empty.price";
	public static final String WRONG_PRICE_FORMAT = "wrong.price.format";

	// parameter name for setting validator in transformer
	public static final String VALIDATOR = "validator";

	private STConstant() {
	}
}
